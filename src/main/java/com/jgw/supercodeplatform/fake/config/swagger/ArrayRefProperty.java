package com.jgw.supercodeplatform.fake.config.swagger;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.refs.GenericRef;
import io.swagger.models.refs.RefType;

/**
 * 同时拥有ArrayProperty和RefProperty的特性
 *
 * @author ChenZhiPing 2018年10月30日 下午6:08:57
 */
public class ArrayRefProperty extends ArrayProperty {
    private GenericRef genericRef;

    public String get$ref() {
        return genericRef.getRef();
    }

    public void set$ref(String ref) {
        this.genericRef = new GenericRef(RefType.DEFINITION, ref);

        // $ref
        RefProperty items = new RefProperty();
        items.setType(ref);
        items.set$ref(ref);
        this.items(items);
    }
}
