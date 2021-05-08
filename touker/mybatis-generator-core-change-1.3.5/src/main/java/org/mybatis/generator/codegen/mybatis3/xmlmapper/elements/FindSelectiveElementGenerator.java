package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * Created by zhangweikang on 2017/9/13.
 */
public class FindSelectiveElementGenerator extends AbstractXmlElementGenerator {

    public FindSelectiveElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {

        XmlElement sqlXmlElement = new XmlElement("select");

        sqlXmlElement.addAttribute(new Attribute("id",introspectedTable.getFindSelectiveId()));
        sqlXmlElement.addAttribute(new Attribute("resultMap",introspectedTable.getBaseResultMapId()));
        sqlXmlElement.addAttribute(new Attribute("parameterType",introspectedTable.getBaseRecordType()));

        context.getCommentGenerator().addComment(sqlXmlElement);

        sqlXmlElement.addElement(new TextElement("select")); //$NON-NLS-1$

        sqlXmlElement.addElement(getBaseColumnListElement());

        StringBuffer sb = new StringBuffer();
        sb.append("from ");
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sqlXmlElement.addElement(new TextElement(sb.toString()));
        sqlXmlElement.addElement(getQueryParamIncludeElement());

        if (context.getPlugins().sqlMapFindSelectiveElementGenerated(
                sqlXmlElement, introspectedTable)) {
            parentElement.addElement(sqlXmlElement);
        }
    }

}
