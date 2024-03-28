package cardgame;

import java.util.EnumMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Attr;

@AllArgsConstructor
public class Card {

    @Getter
    private int Id;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private EnumMap<Attribute, Integer> attributesAccepted;
    @Getter
    private EnumMap<Attribute, Integer> attributesNotAccepted;



}
