//package com.lens.chatter;
//
//import com.lens.chatter.enums.SearchOperator;
//import com.lens.chatter.model.entity.Product;
//import com.lens.chatter.model.entity.ProductType;
//import com.lens.chatter.model.other.SearchCriteria;
//import com.lens.chatter.repository.ProductRepository;
//import com.lens.chatter.repository.specifications.ProductSpecification;
//import org.assertj.core.api.Assertions;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.ZonedDateTime;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//
///**
// * Created by Emir Gökdemir
// * on 3 Kas 2020
// */
//
//@RunWith(SpringRunner.class)
////@ContextConfiguration(classes = { PersistenceJPAConfig.class })
//@ContextConfigurationde
//@Transactional
//@DataJpaTest
//public class JPASpecificationTest {
//
//    @Autowired
//    private ProductRepository repository;
//
//    private Product product;
//    private Product product1;
//    private ProductType productType;
//
//    @Before
//    public void init() {
//
//        productType = new ProductType();
//        productType.setColor("red");
//        productType.setDescription("sad");
//        productType.setHeight(10F);
//        productType.setLength(15F);
//        productType.setWidth(10F);
//        productType.setMaterialType("demir");
//        productType.setName("test tipi");
//
//        product = new Product();
//        product.setProductionNo("11111111");
//        product.setProductionTime(ZonedDateTime.now());
//        product.setProductType(productType);
//        product.setShift(1);
//        repository.save(product);
//
//        product1 = new Product();
//        product1.setProductionNo("22222211");
//        product1.setProductionTime(ZonedDateTime.now());
//        product1.setProductType(productType);
//        product1.setShift(1);
//        repository.save(product1);
//    }
//
//    @Test
//    public void givenLast_whenGettingListOfUsers_thenCorrect() {
//        ProductSpecification spec =
//                new ProductSpecification();
//        spec.add(new SearchCriteria("productionNo", "11111111", SearchOperator.EQUAL ));
//
//        List<Product> results = repository.findAll(spec);
//
//
//        Assert.assertThat(results, hasItem(product));
//    }
//}
