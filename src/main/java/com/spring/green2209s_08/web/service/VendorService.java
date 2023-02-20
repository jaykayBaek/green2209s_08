package com.spring.green2209s_08.web.service;

import com.spring.green2209s_08.web.domain.Vendor;
import com.spring.green2209s_08.web.exception.errorResult.VendorErrorResult;
import com.spring.green2209s_08.web.exception.VendorException;
import com.spring.green2209s_08.web.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateLoginId(String loginId){
        Optional<Vendor> findVendor = vendorRepository.findByVendorLoginId(loginId);
        if(findVendor.isPresent()){
            throw new VendorException(VendorErrorResult.DUPLICATE_OR_LEAVED_LOGIN_ID);
        }
    }

    public void register(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    public Optional<Vendor> findById(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor;
    }

    public Long login(String vendorLoginId, String vendorPassword) {
        Optional<Vendor> vendor = vendorRepository.findByVendorLoginId(vendorLoginId);

        if(vendor.isEmpty()){
            throw new VendorException(VendorErrorResult.LOGIN_FAIL);
        }
        if(!passwordEncoder.matches(vendorPassword, vendor.get().getVendorPassword())){
            throw new VendorException(VendorErrorResult.LOGIN_FAIL);
        }

        return vendor.get().getId();
    }

    public void vendorPasswordCheck(Long vendorId, String vendorPassword) {
        Vendor findVendor = vendorRepository.findById(vendorId).get();
        boolean result = passwordEncoder.matches(vendorPassword, findVendor.getVendorPassword());
        if(result == false){
            throw new VendorException(VendorErrorResult.PASSWORD_NOT_MATCH);
        }
    }
}
