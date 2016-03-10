package com.kfive.hopebible.Pojo;

/**
 * Created by apple on 10/26/14.
 */
public class BookList {
    private static final String[][] BIBLE_BOOKS = {
            //Array format [Book_Name,Book_Code,Chapter_Count]
            {"Genesis","01","50"},
            {"Exodus","02","40"},
            {"Leviticus","03","27"},
            {"Numbers","04","36"},
            {"Deuteronomy","05","34"},
            {"Joshua","06","24"},
            {"Judges","07","21"},
            {"Ruth","08","4"},
            {"1 Samuel","09","31"},
            {"2 Samuel","10","24"},
            {"1 Kings","11","22"},
            {"2 Kings","12","25"},
            {"1 Chronicles","13","29"},
            {"2 Chronicles","14","36"},
            {"1 Ezra","15","10"},
            {"1 Nehemiah","16","13"},
            {"Esther","17","10"},
            {"Job","18","42"},
            {"Psalms","19","150"},
            {"Proverbs","20","31"},
            {"Ecclesiastes","21","12"},
            {"Songs of Solomon","22","8"},
            {"Isaiah","23","66"},
            {"Jeremiah","24","52"},
            {"Lamentations","25","5"},
            {"Ezekiel","26","48"},
            {"Daniel","27","12"},
            {"Hosea","28","14"},
            {"Joel","29","3"},
            {"Amos","30","9"},
            {"Obadiah","31","1"},
            {"Jonah","32","4"},
            {"Micah","33","7"},
            {"Nahum","34","3"},
            {"Habakkuk","35","3"},
            {"Zephaniah","36","3"},
            {"Haggai","37","2"},
            {"Zechariah","38","14"},
            {"Malachi","39","4"},
            {"Matthew","40","28"},
            {"Mark","41","16"},
            {"Luke","42","24"},
            {"John","43","21"},
            {"Acts","44","28"},
            {"Romans","45","16"},
            {"1 Corinthians","46","16"},
            {"2 Corinthians","47","13"},
            {"Galatians","48","6"},
            {"Ephesians","49","6"},
            {"Philippians","50","4"},
            {"Colossians","51","4"},
            {"1 Thessalonians","52","5"},
            {"2 Thessalonians","53","3"},
            {"1 Timothy","54","6"},
            {"2 Timothy","55","4"},
            {"Titus","56","3"},
            {"Philemon","57","1"},
            {"Hebrews","58","13"},
            {"James","59","5"},
            {"1 Peter","60","5"},
            {"2 Peter","61","3"},
            {"1 John","62","5"},
            {"2 John","63","1"},
            {"3 John","64","1"},
            {"Jude","65","1"},
            {"Revelation","66","22"},

    };

    private static final String[][] NEW_TESTAMENT = {
            {"Matthew","40","28"},
            {"Mark","41","16"},
            {"Luke","42","24"},
            {"John","43","21"},
            {"Acts","44","28"},
            {"Romans","45","16"},
            {"1 Corinthians","46","16"},
            {"2 Corinthians","47","13"},
            {"Galatians","48","6"},
            {"Ephesians","49","6"},
            {"Philippians","50","4"},
            {"Colossians","51","4"},
            {"1 Thessalonians","52","5"},
            {"2 Thessalonians","53","3"},
            {"1 Timothy","54","6"},
            {"2 Timothy","55","4"},
            {"Titus","56","3"},
            {"Philemon","57","1"},
            {"Hebrews","58","13"},
            {"James","59","5"},
            {"1 Peter","60","5"},
            {"2 Peter","61","3"},
            {"1 John","62","5"},
            {"2 John","63","1"},
            {"3 John","64","1"},
            {"Jude","65","1"},
            {"Revelation","66","22"},

    }; private static final String[][] OLD_TESTAMENT = {
            {"Genesis","01","50"},
            {"Exodus","02","40"},
            {"Leviticus","03","27"},
            {"Numbers","04","36"},
            {"Deuteronomy","05","34"},
            {"Joshua","06","24"},
            {"Judges","07","21"},
            {"Ruth","08","4"},
            {"1 Samuel","09","31"},
            {"2 Samuel","10","24"},
            {"1 Kings","11","22"},
            {"2 Kings","12","25"},
            {"1 Chronicles","13","29"},
            {"2 Chronicles","14","36"},
            {"1 Ezra","15","10"},
            {"1 Nehemiah","16","13"},
            {"Esther","17","10"},
            {"Job","18","42"},
            {"Psalms","19","150"},
            {"Proverbs","20","31"},
            {"Ecclesiastes","21","12"},
            {"Song of Songs","22","8"},
            {"Isaiah","23","66"},
            {"Jeremiah","24","52"},
            {"Lamentations","25","5"},
            {"Ezekiel","26","48"},
            {"Daniel","27","12"},
            {"Hosea","28","14"},
            {"Joel","29","3"},
            {"Amos","30","9"},
            {"Obadiah","31","1"},
            {"Jonah","32","4"},
            {"Micah","33","7"},
            {"Nahum","34","3"},
            {"Habakkuk","35","3"},
            {"Zephaniah","36","3"},
            {"Haggai","37","2"},
            {"Zechariah","38","14"},
            {"Malachi","39","4"},

    };
    public BookList() {

    }

    public static String[][] getBibleBooks() {

        return BIBLE_BOOKS;
    }

    public static String[][] getNewTestament() {
        return NEW_TESTAMENT;
    }

    public static String[][] getOldTestament() {
        return OLD_TESTAMENT;
    }
}
