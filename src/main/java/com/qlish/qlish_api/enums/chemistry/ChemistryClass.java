package com.qlish.qlish_api.enums.chemistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChemistryClass {
    GENERAL_CHEMISTRY("general-chemistry"),
    ORGANIC_CHEMISTRY("organic-chemistry"),
    ENVIRONMENTAL_CHEMISTRY("environmental-chemistry"),
    INORGANIC_CHEMISTRY("inorganic-chemistry"),
    PHYSICAL_CHEMISTRY("physical-chemistry"),
    BIOCHEMISTRY("biochemistry"),
    ANALYTICAL_CHEMISTRY("analytical-chemistry"),
    NUCLEAR_CHEMISTRY("nuclear-chemistry"),
    POLYMER_CHEMISTRY("polymer-chemistry");

    private final String className;

    public static ChemistryClass fromClassName(String className){
        for (ChemistryClass item: ChemistryClass.values()){
            if(item.getClassName().equalsIgnoreCase(className))
                return item;
        }
        return null;
    }



}
