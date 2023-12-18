package com.example.emb.entity;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author KLE1NBLUE&LackOxy
 * @since 2023-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Mettable对象", description="")
public class Mettable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String photoUrl;

    private Integer num;

    private Integer used;

    private String infoUrl;

    private Date modifiedTime;


}
