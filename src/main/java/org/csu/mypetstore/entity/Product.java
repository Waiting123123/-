package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("product")
public class Product {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String sku;

    @TableField("category_id")
    private Long categoryId;

    private String name;

    private String summary;

    private String description;

    @TableField("cover_image")
    private String coverImage;

    private BigDecimal price;

    private Integer stock;

    private String status;

    @TableField("sort_order")
    private Integer sortOrder;

    private Long version;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
