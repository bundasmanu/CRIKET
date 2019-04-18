/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import controller.CategoryBean;
import cricketdto.CategoryDTO;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryDTOConverter")
public class CategoryDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String id) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{categoryBean}", CategoryBean.class);

        CategoryBean categoryBean = (CategoryBean)vex.getValue(ctx.getELContext());
        
        CategoryDTO categoryDTO = categoryBean.findCategoryDTOById(Integer.valueOf(id));
        System.out.println("\n\n\n\n\n\nCategoryDTOConverter - " + categoryDTO);
        return categoryDTO;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object entity) {
        return ((CategoryDTO)entity).getIdCategory().toString();
    }
    
}
