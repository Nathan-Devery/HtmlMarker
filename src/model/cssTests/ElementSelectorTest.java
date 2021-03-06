package model.cssTests;

import jdk.nashorn.api.tree.CompilationUnitTree;
import model.TestResult;
import model.Testable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.w3c.css.sac.CSSParseException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.util.ArrayList;

/**
 * Tests there is the use of an element selector. Tests the html element and css rule are not empty.
 * Full marks: Present
 */
public class ElementSelectorTest extends Testable {

    public ElementSelectorTest(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return  "Tests there is the use of an element selector. Tests the html element and css rule are not empty.\n" +
                " * Full marks: Present";
    }

    @Override
    public TestResult runTest(ArrayList<Document> documents, ArrayList<Document> xmlDocs, CSSStyleSheet sheet, String cssDocString, CompilationUnitTree tree, double percentage) {
        double result = 0;
        String report = "";

        CSSRuleList ruleList = sheet.getCssRules();

        for (int i = 0; i < ruleList.getLength(); i++) {
            CSSRule rule = ruleList.item(i);
            if (rule instanceof CSSStyleRule) {
                CSSStyleRule styleRule = (CSSStyleRule) rule;
                String prefix = styleRule.getSelectorText();
                if(!(prefix.startsWith(".") || prefix.startsWith("#")) && styleRule.getStyle().getLength() != 0){   //Check css isn't empty
                    for (Document document : documents) {
                        try {
                            Elements affectedElements = document.select(styleRule.getSelectorText());
                            for (Element element : affectedElements) {
                                if (!(element.childNodes().isEmpty())) {    //Check if corresponding html element actually holds something for the css to apply to
                                    result = percentage;
                                }
                            }
                        }catch(Selector.SelectorParseException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return new TestResult(toString(), result, report);
    }
}
