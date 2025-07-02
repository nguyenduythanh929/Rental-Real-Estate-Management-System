package com.javaweb.controller.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.enums.Status;
import com.javaweb.enums.Transaction;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ITransactionService;
import com.javaweb.service.impl.CustomerService;
import com.javaweb.service.impl.UserService;
import com.javaweb.utils.DisplayTagUtils;
import com.javaweb.utils.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
@Controller(value = "customerControllerOfAdmin")
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageUtils messageUtil;

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/admin/customer-list")
    public ModelAndView getAllCustomers(@ModelAttribute(SystemConstant.MODEL) CustomerSearchRequest customerSearchRequest, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/list");
        mav.addObject("modelSearch", customerSearchRequest);
        mav.addObject("staffs", userService.getStaffs());
        mav.addObject("status", Status.getStatus());
        DisplayTagUtils.of(request, customerSearchRequest);
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            customerSearchRequest.setStaffId(staffId);
        }
        List<CustomerSearchResponse> customers = customerService.fillAll(customerSearchRequest,
                PageRequest.of(customerSearchRequest.getPage() - 1, customerSearchRequest.getMaxPageItems()));
        customerSearchRequest.setListResult(customers);
        customerSearchRequest.setTotalItem(customerService.countTotalItem(customerSearchRequest));
        mav.addObject(SystemConstant.MODEL, customerSearchRequest);
        initMessageResponse(mav, request);
        return mav;
    }

    @GetMapping("/admin/customer-edit")
    public ModelAndView createCustomer(@ModelAttribute CustomerDTO customerDTO){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("status", Status.getStatus());
        return mav;
    }

    @GetMapping("/admin/customer-edit-{id}")
    public ModelAndView updateCustomer(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerDTO customerDTO = customerService.findById(id);
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("status", Status.getStatus());
        mav.addObject("transaction", Transaction.getTransaction());
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            if(customerService.checkAssignedStaff(id, staffId)){
                mav.setViewName("redirect:/error/404");
                return mav;
            }
        }
        List<TransactionEntity> CSKH = transactionService.findByCodeAndCustomerId("CSKH", id);
        List<TransactionEntity> DDX = transactionService.findByCodeAndCustomerId("DDX", id);
        mav.addObject("CSKH", CSKH);
        mav.addObject("DDX", DDX);
        return mav;
    }

    private void initMessageResponse(ModelAndView mav, HttpServletRequest request) {
        String message = request.getParameter("message");
        if (message != null && StringUtils.isNotEmpty(message)) {
            Map<String, String> messageMap = messageUtil.getMessage(message);
            mav.addObject(SystemConstant.ALERT, messageMap.get(SystemConstant.ALERT));
            mav.addObject(SystemConstant.MESSAGE_RESPONSE, messageMap.get(SystemConstant.MESSAGE_RESPONSE));
        }
    }
}
