package org.csu.mypetstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("category")
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String code;

    @TableField("parent_id")
    private Long parentId;

    // parent_key 由数据库根据 parent_id 生成，不声明为实体字段，避免参与写入。

    private String name;

    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
