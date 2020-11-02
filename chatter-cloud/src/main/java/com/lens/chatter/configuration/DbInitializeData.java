package com.lens.chatter.configuration;


import com.lens.chatter.enums.Role;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.FirmRepository;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 17 Nis 2020
 */
@Component
public class DbInitializeData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostConstruct
    private void postConstruct() {
        if (!firmRepository.existsByName("default firm")) {
            Firm defaultFirm = new Firm();
            defaultFirm.setName("default firm");
            defaultFirm.setCity("Erzurum");
            //TODO: Taha'dan alınıp ayarlanacak.
            defaultFirm.setAddress(" Adres ");
            defaultFirm.setPhone("02161001010");
            defaultFirm.setEmail("firm@lenstech.vision");
            firmRepository.save(defaultFirm);
        }

        if (!branchRepository.existsByName("default branch")) {
            Branch defaultBranch = new Branch();
            defaultBranch.setName("default branch");
            defaultBranch.setCity("İstanbul");
            defaultBranch.setAddress("Boğaziçi Teknopark");
            defaultBranch.setFirm(firmRepository.findByName("default firm"));
            defaultBranch.setDailyShiftQuantity(3);
            branchRepository.save(defaultBranch);
        }

        if (!departmentRepository.existsByName("default department")) {
            Department defaultDepartment = new Department();
            defaultDepartment.setName("default department");
            defaultDepartment.setBranch(branchRepository.findByName("default branch"));
            defaultDepartment.setDescription("default description");
            departmentRepository.save(defaultDepartment);
        }

        Department department = departmentRepository.findTopByName("default department");

        List<User> initialUsers = new ArrayList<>();
        if (!userRepository.existsByEmail("admin@lenstech.vision")) {
            User admin = new User();
            admin.setEmail("admin@lenstech.vision");
            admin.setPassword("$2a$10$mLpoOQQ1mf9217XGIBoW4.QOoMPSenH0hm8MU8Hwx2V6ycCA6DJIa");
            admin.setName("admin");
            admin.setSurname("admin");
            admin.setRole(Role.ADMIN);
            admin.setConfirmed(true);
            admin.setDepartment(department);
            admin.setTitle("yazılım kralı");
            initialUsers.add(admin);
        }
        if (!userRepository.existsByEmail("basic@lenstech.vision")) {
            User basicUser = new User();
            basicUser.setEmail("basic@lenstech.vision");
            basicUser.setPassword("$2a$10$mLpoOQQ1mf9217XGIBoW4.QOoMPSenH0hm8MU8Hwx2V6ycCA6DJIa");
            basicUser.setName("default");
            basicUser.setSurname("basic");
            basicUser.setRole(Role.BASIC_USER);
            basicUser.setConfirmed(true);
            basicUser.setDepartment(department);
            basicUser.setTitle("üretim stajyeri");
            initialUsers.add(basicUser);
        }
        if (!userRepository.existsByEmail("branch@lenstech.vision")) {
            User branchAdmin = new User();
            branchAdmin.setEmail("branch@lenstech.vision");
            branchAdmin.setPassword("$2a$10$mLpoOQQ1mf9217XGIBoW4.QOoMPSenH0hm8MU8Hwx2V6ycCA6DJIa");
            branchAdmin.setName("default");
            branchAdmin.setSurname("branchAdmin");
            branchAdmin.setRole(Role.BRANCH_ADMIN);
            branchAdmin.setConfirmed(true);
            branchAdmin.setDepartment(department);
            branchAdmin.setTitle("ordu fabrika müdürü");
            initialUsers.add(branchAdmin);
        }
        if (!userRepository.existsByEmail("firm@lenstech.vision")) {
            User firmAdmin = new User();
            firmAdmin.setEmail("firm@lenstech.vision");
            firmAdmin.setPassword("$2a$10$mLpoOQQ1mf9217XGIBoW4.QOoMPSenH0hm8MU8Hwx2V6ycCA6DJIa");
            firmAdmin.setName("default");
            firmAdmin.setSurname("firmAdmin");
            firmAdmin.setRole(Role.FIRM_ADMIN);
            firmAdmin.setConfirmed(true);
            firmAdmin.setDepartment(department);
            firmAdmin.setTitle("genel müdür yardımcısı");
            initialUsers.add(firmAdmin);
        }
        if (!initialUsers.isEmpty()) {
            userRepository.saveAll(initialUsers);
        }
    }
}
