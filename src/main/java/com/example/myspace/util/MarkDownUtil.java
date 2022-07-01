package com.example.myspace.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

/**
 * 转换md格式为html
 */
public class MarkDownUtil {
    public static String mdToHtml(String markdownString) {
        if (markdownString == null || "".equals(markdownString)) {
            return "";
        }
        java.util.List<Extension> extensions = List.of(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        return content;
    }
}
