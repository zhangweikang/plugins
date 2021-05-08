package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * Created by zhangweikang on 2017/9/13.
 */
public class QueryParamElementGenerator extends AbstractXmlElementGenerator {

    public QueryParamElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement sqlXmlElement = new XmlElement("sql");

        sqlXmlElement.addAttribute(new Attribute("id",introspectedTable.getQueryParamId()));

        context.getCommentGenerator().addComment(sqlXmlElement);

        XmlElement whereXmlElement = new XmlElement("where");

        sqlXmlElement.addElement(whereXmlElement);

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getAllColumns())) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            whereXmlElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append("AND ");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapQueryParamElementGenerated(
                sqlXmlElement, introspectedTable)) {
            parentElement.addElement(sqlXmlElement);
        }
    }
}
