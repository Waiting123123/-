package org.csu.mypetstore.mapper;

import org.csu.mypetstore.entity.Category;
import org.csu.mypetstore.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 依赖已有教学数据；不执行初始化脚本，不自动恢复被修改的数据。
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.sql.init.mode=never")
@ActiveProfiles("dev")
@Transactional(readOnly = true)
class CatalogMapperIntegrationTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    void selectByIdMapsSecondaryCategory() {
        Category category = categoryMapper.selectById(102L);

        assertNotNull(category, "需要已有教学二级分类 id=102；测试不会补建数据");
        assertAll(
                () -> assertEquals(102L, category.getId()),
                () -> assertEquals("DOGS_SUPPLIES", category.getCode()),
                () -> assertEquals(1L, category.getParentId()),
                () -> assertEquals("用品", category.getName()),
                () -> assertEquals(20, category.getSortOrder()),
                () -> assertNotNull(category.getCreatedAt()),
                () -> assertNotNull(category.getUpdatedAt()));
        assertTrue(!category.getUpdatedAt().isBefore(category.getCreatedAt()),
                "分类更新时间不得早于创建时间");
    }

    @Test
    void selectByIdMapsProduct() {
        Product product = productMapper.selectById(4L);

        assertNotNull(product, "需要已有教学商品 id=4；测试不会补建数据");
        assertAll(
                () -> assertEquals(4L, product.getId()),
                () -> assertEquals("K9-LEASH-01", product.getSku()),
                () -> assertEquals(102L, product.getCategoryId()),
                () -> assertEquals("犬用出行套装", product.getName()),
                () -> assertEquals("用品类教学商品，展示两位小数金额。", product.getSummary()),
                () -> assertEquals("犬用出行套装：宠物商城课程教学商品。用品类教学商品，展示两位小数金额。"
                        + "商品及价格仅用于教学演示；请通过实际下单观察订单快照与库存事务。",
                        product.getDescription()),
                () -> assertEquals("images/products/K9-RTV-11/1.jpg", product.getCoverImage()),
                () -> assertEquals(new BigDecimal("49.90"), product.getPrice()),
                () -> assertEquals(80, product.getStock()),
                () -> assertEquals("ON_SALE", product.getStatus()),
                () -> assertEquals(40, product.getSortOrder()),
                () -> assertEquals(0L, product.getVersion()),
                () -> assertNotNull(product.getCreatedAt()),
                () -> assertNotNull(product.getUpdatedAt()));
        assertTrue(!product.getUpdatedAt().isBefore(product.getCreatedAt()),
                "商品更新时间不得早于创建时间");
    }
}
