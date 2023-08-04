package commonStructures;

import utilities.ConversionUtils;

public enum City {
    NONE,
    ATLANTA,
    BEIJING,
    DUBAI,
    LOS_ANGELES,
    CHICAGO,
    LONDON,
    TOKYO,
    SHANGHAI,
    PARIS,
    AMSTERDAM,
    DALLAS,
    GUANGZHOU,
    FRANKFURT,
    ISTANBUL,
    DELHI,
    JAKARTA,
    SINGAPORE,
    SEOUL,
    DENVER,
    BANGKOK,
    NEW_YORK,
    KUALA_LUMPUR,
    SAN_FRANCISCO,
    MADRID,
    CHENGDU,
    LAS_VEGAS,
    BARCELONA,
    MUMBAI,
    TORONTO,
    SEATTLE,
    CHARLOTTE,
    SHENZHEN,
    TAIPEI,
    MEXICO_CITY,
    KUNMING,
    MUNICH,
    ORLANDO,
    MIAMI,
    PHOENIX,
    SYDNEY,
    NEWARK,
    MANILA,
    XIAN,
    ROME,
    HOUSTON,
    MOSCOW,
    CHONGQING,
    MINNEAPOLIS,
    SAO_PAULO,
    BOSTON,
    HO_CHI_MINH,
    DOHA,
    HANGZHOU,
    DETROIT,
    JEDDAH,
    MELBOURNE,
    FORT_LAUDERDALE,
    BOGOTA,
    JEJU,
    PHILADELPHIA,
    DUBLIN,
    ZURICH,
    COPENHAGEN,
    OSAKA,
    PALMA_DE_MALLORCA,
    MANCHESTER,
    OSLO,
    LISBON,
    STOCKHOLM,
    BALTIMORE,
    ANTALYA,
    NANJING, BANGALORE,
    RIYADH,
    BRUSSELS,
    DUSSELDORF,
    XIAMEN,
    VIENNA,
    ZHENGZHOU,
    SALT_LAKE_CITY,
    VANCOUVER,
    WASHINGTON,
    CHANGSHA,
    ABU_DHABI,
    CANCUN,
    FUKUOKA,
    QINGDAO,
    BRISBANE,
    WUHAN;

    @Override
    public String toString() {
        return capitalizeEachWord(name().replace('_', ' '));
    }

    private String capitalizeEachWord(String cityValue) {
        StringBuilder capitalizedCity = new StringBuilder();
        String[] cityWords = cityValue.toLowerCase().split(" ");
        for (String word : cityWords)
            capitalizedCity.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");

        return capitalizedCity.toString().trim();
    }

    public static City getValue(String cityName) {
        return City.valueOf(cityName.replace(' ', '_').toUpperCase());
    }
}