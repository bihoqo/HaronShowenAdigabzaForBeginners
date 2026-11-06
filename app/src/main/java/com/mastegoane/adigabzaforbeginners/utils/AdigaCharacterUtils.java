package com.mastegoane.adigabzaforbeginners.utils;

import android.util.ArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class AdigaCharacterUtils {

    private AdigaCharacterUtils() {
        mSpecial2EnglishCharacterMap = new ArrayMap<>();
        mCyrillicExtra2BasicCharacterMap = new ArrayMap<>();
        mCyrillic2LatinSingleCharMap = new ArrayMap<>();
        mCyrillic2LatinRegexp4Chars = new ArrayMap<>();
        mCyrillic2LatinRegexp3Chars = new ArrayMap<>();
        mCyrillic2LatinRegexp2Chars = new ArrayMap<>();
        mCyrillic2LatinRegexpPrefixes1Char = new ArrayMap<>();
        mCyrillic2LatinRegexpPrefixes2Chars = new ArrayMap<>();
        mCyrillic2LatinRegexpPrefixes3Chars = new ArrayMap<>();
        mCyrillic2LatinRegexpPostfixes = new ArrayMap<>();

        mLatin2CyrillicSingleCharMap = new ArrayMap<>();
        mLatin2CyrillicRegexp4Chars = new ArrayMap<>();
        mLatin2CyrillicRegexp3Chars = new ArrayMap<>();
        mLatin2CyrillicRegexp2Chars = new ArrayMap<>();
        mLatin2CyrillicRegexpPrefixes1Char = new ArrayMap<>();
        mLatin2CyrillicRegexpPrefixes2Chars = new ArrayMap<>();
        mLatin2CyrillicRegexpPrefixes3Chars = new ArrayMap<>();
        mLatin2CyrillicRegexpPostfixes = new ArrayMap<>();
        initialize();
    }

    public static synchronized AdigaCharacterUtils inst() {
        return mInstance;
    }

    private void initialize() {
        mSpecial2EnglishCharacterMap.put("á", "a");
        mSpecial2EnglishCharacterMap.put("ç", "c");
        mSpecial2EnglishCharacterMap.put("ć", "c");
        mSpecial2EnglishCharacterMap.put("é", "e");
        mSpecial2EnglishCharacterMap.put("ǵ", "g");
        mSpecial2EnglishCharacterMap.put("ğ", "g");
        mSpecial2EnglishCharacterMap.put("ḣ", "h");
        mSpecial2EnglishCharacterMap.put("ı", "i");
        mSpecial2EnglishCharacterMap.put("ḱ", "k");
        mSpecial2EnglishCharacterMap.put("ĺ", "l");
        mSpecial2EnglishCharacterMap.put("ö", "o");
        mSpecial2EnglishCharacterMap.put("ṕ", "p");
        mSpecial2EnglishCharacterMap.put("ş", "s");
        mSpecial2EnglishCharacterMap.put("ś", "s");
        mSpecial2EnglishCharacterMap.put("š", "s");
        mSpecial2EnglishCharacterMap.put("ṫ", "t");
        mSpecial2EnglishCharacterMap.put("ü", "u");
        mSpecial2EnglishCharacterMap.put("ź", "z");
        mSpecial2EnglishCharacterMap.put("Á", "A");
        mSpecial2EnglishCharacterMap.put("Ç", "C");
        mSpecial2EnglishCharacterMap.put("Ć", "C");
        mSpecial2EnglishCharacterMap.put("É", "E");
        mSpecial2EnglishCharacterMap.put("Ǵ", "G");
        mSpecial2EnglishCharacterMap.put("Ğ", "G");
        mSpecial2EnglishCharacterMap.put("Ḣ", "H");
        mSpecial2EnglishCharacterMap.put("İ", "I");
        mSpecial2EnglishCharacterMap.put("Ḱ", "K");
        mSpecial2EnglishCharacterMap.put("Ĺ", "L");
        mSpecial2EnglishCharacterMap.put("Ö", "O");
        mSpecial2EnglishCharacterMap.put("Ṕ", "P");
        mSpecial2EnglishCharacterMap.put("Ş", "S");
        mSpecial2EnglishCharacterMap.put("Ś", "S");
        mSpecial2EnglishCharacterMap.put("Š", "S");
        mSpecial2EnglishCharacterMap.put("Ṫ", "T");
        mSpecial2EnglishCharacterMap.put("Ü", "U");
        mSpecial2EnglishCharacterMap.put("Ź", "Z");

        //I
        mCyrillicExtra2BasicCharacterMap.put("й", "и");
        mCyrillicExtra2BasicCharacterMap.put("Й", "И");
        mCyrillicExtra2BasicCharacterMap.put("щ", "ш");
        mCyrillicExtra2BasicCharacterMap.put("Щ", "Ш");
        mCyrillicExtra2BasicCharacterMap.put("1", "Ӏ");
        mCyrillicExtra2BasicCharacterMap.put("l", "Ӏ"); //English lower-case L
        mCyrillicExtra2BasicCharacterMap.put("i", "Ӏ");
        mCyrillicExtra2BasicCharacterMap.put("I", "Ӏ");
        mCyrillicExtra2BasicCharacterMap.put("ı", "Ӏ");
        mCyrillicExtra2BasicCharacterMap.put("İ", "Ӏ");
        mCyrillicExtra2BasicCharacterMap.put("t", "т");
        mCyrillicExtra2BasicCharacterMap.put("T", "Т");
        mCyrillicExtra2BasicCharacterMap.put("a", "а"); // Latin a to Cyrillic a
        mCyrillicExtra2BasicCharacterMap.put("A", "А"); // same
        mCyrillicExtra2BasicCharacterMap.put("o", "о"); // Latin o to Cyrillic o
        mCyrillicExtra2BasicCharacterMap.put("O", "О"); // same

        mCyrillicExtra2BasicCharacterMap.put("H", "Н"); // Latin H to Cyrillic H

        mCyrillicExtra2BasicCharacterMap.put("B", "В"); // Latin B to Cyrillic B
        mCyrillicExtra2BasicCharacterMap.put("b", "Ь"); // same

        mCyrillicExtra2BasicCharacterMap.put("E", "Е"); // Latin E to Cyrillic E
        mCyrillicExtra2BasicCharacterMap.put("e", "е"); // same

        mCyrillicExtra2BasicCharacterMap.put("K", "К"); // Latin K to Cyrillic K
        mCyrillicExtra2BasicCharacterMap.put("k", "к"); // same

        mCyrillicExtra2BasicCharacterMap.put("M", "М"); // Latin M to Cyrillic M
        mCyrillicExtra2BasicCharacterMap.put("m", "м"); // same

        mCyrillicExtra2BasicCharacterMap.put("P", "Р"); // Latin P to Cyrillic P
        mCyrillicExtra2BasicCharacterMap.put("p", "р"); // same

        mCyrillicExtra2BasicCharacterMap.put("C", "С"); // Latin C to Cyrillic C
        mCyrillicExtra2BasicCharacterMap.put("c", "с"); // same

        mCyrillicExtra2BasicCharacterMap.put("Y", "У"); // Latin Y to Cyrillic Y
        mCyrillicExtra2BasicCharacterMap.put("y", "у"); // same

        mCyrillicExtra2BasicCharacterMap.put("X", "Х"); // Latin X to Cyrillic X
        mCyrillicExtra2BasicCharacterMap.put("x", "х"); // same

        /*
        А Б В Г Д Е Ё Ж З И Й К Л М Н О Ӏ П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я
        а б в г д е ё ж з и й к л м н о   п р с т у ф х ц ч ш щ ъ ы ь э ю я
        АДЫГЭ
        аtын

        Гъу Жъу КӀу Шъу ШӀу ПӀу Хъу Къу ТӀу Дзу
        Гу Гъ Дж Дз Жь КӀ Ку Къ ЛӀ Лъ Шъ ПӀ ТӀ Хъ Хь ЦӀ Жъ Цу ЧӀ ЧЪ ШӀ Ӏу
        А Б В Г Д Е Ё Ж З И Й К Л М Н О Ӏ П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я

        áçćéǵğḣıḱĺöṕşśšṫüź
        ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
        */

        //========================================================
        //========= Cyrillic-to-Latin =========
        //========================================================
        // 4 character letters

        // NOTE the upper case and lower case of the Cyrillic "I" looks the same as follows respectively: uc: Ӏ, lc: ӏ
        mCyrillic2LatinRegexp4Chars.put("кӏуа", "quá"); // first type of 'а'
        mCyrillic2LatinRegexp4Chars.put("кiуа", "quá"); // first type of 'а'
        mCyrillic2LatinRegexp4Chars.put("кӀуа", "quá"); // first type of 'а'
        mCyrillic2LatinRegexp4Chars.put("кӏуa", "quá"); // second type of 'a'
        mCyrillic2LatinRegexp4Chars.put("кiуa", "quá"); // second type of 'a'
        mCyrillic2LatinRegexp4Chars.put("кӀуa", "quá"); // second type of 'a'
        mCyrillic2LatinRegexp4Chars.put("кlуa", "quá"); // second type of 'a'

        mCyrillic2LatinRegexp4Chars.put("kӏуа", "quá"); // first type of 'а', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kiуа", "quá"); // first type of 'а', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kӀуа", "quá"); // first type of 'а', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kӏуa", "quá"); // second type of 'a', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kiуa", "quá"); // second type of 'a', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kӀуa", "quá"); // second type of 'a', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("klуa", "quá"); // second type of 'a', latin 'k'


        mCyrillic2LatinRegexp4Chars.put("кӏуэ", "qo"); // first type of 'а'
        mCyrillic2LatinRegexp4Chars.put("кiуэ", "qo"); // first type of 'а'
        mCyrillic2LatinRegexp4Chars.put("кӀуэ", "qo"); // first type of 'а'
//        mCyrillic2LatinRegexp4Chars.put("кӏуэ", "qo"); // second type of 'a'
//        mCyrillic2LatinRegexp4Chars.put("кiуэ", "qo"); // second type of 'a'
//        mCyrillic2LatinRegexp4Chars.put("кӀуэ", "qo"); // second type of 'a'
        mCyrillic2LatinRegexp4Chars.put("кlуэ", "qo"); // second type of 'a'

        mCyrillic2LatinRegexp4Chars.put("kӏуэ", "qo"); // first type of 'а', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kiуэ", "qo"); // first type of 'а', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("kӀуэ", "qo"); // first type of 'а', latin 'k'
//        mCyrillic2LatinRegexp4Chars.put("kӏуэ", "qo"); // second type of 'a', latin 'k'
//        mCyrillic2LatinRegexp4Chars.put("kiуэ", "qo"); // second type of 'a', latin 'k'
//        mCyrillic2LatinRegexp4Chars.put("kӀуэ", "qo"); // second type of 'a', latin 'k'
        mCyrillic2LatinRegexp4Chars.put("klуэ", "qo"); // second type of 'a', latin 'k'


        mCyrillic2LatinRegexp4Chars.put("шӏои", "şü'ey");
        mCyrillic2LatinRegexp4Chars.put("шiои", "şü'ey");
        mCyrillic2LatinRegexp4Chars.put("шӀои", "şü'ey");
        mCyrillic2LatinRegexp4Chars.put("щӏои", "şü'ey");
        mCyrillic2LatinRegexp4Chars.put("щiои", "şü'ey");
        mCyrillic2LatinRegexp4Chars.put("щӀои", "şü'ey");

        // 3 character letters
        mCyrillic2LatinRegexp3Chars.put("гъу", "ğu");
        mCyrillic2LatinRegexp3Chars.put("жъу", "jü"); // TODO ???
        mCyrillic2LatinRegexp3Chars.put("жъо", "jö");
        // TODO how to decide between 'q' and 'ć'? add a switch for dialect? can it be general/global?
        mCyrillic2LatinRegexp3Chars.put("кӏу", "qu");
        mCyrillic2LatinRegexp3Chars.put("кiу", "qu");
        mCyrillic2LatinRegexp3Chars.put("кӀу", "qu");
        mCyrillic2LatinRegexp3Chars.put("кlу", "qu");
        mCyrillic2LatinRegexp3Chars.put("кӏо", "qo");
        mCyrillic2LatinRegexp3Chars.put("кiо", "qo");
        mCyrillic2LatinRegexp3Chars.put("кӀо", "qo");
        mCyrillic2LatinRegexp3Chars.put("кӏи", "ćiy");
        mCyrillic2LatinRegexp3Chars.put("кiи", "ćiy");
        mCyrillic2LatinRegexp3Chars.put("кӀи", "ćiy");
        mCyrillic2LatinRegexp3Chars.put("кӏы", "ćı");
        mCyrillic2LatinRegexp3Chars.put("кiы", "ćı");
        mCyrillic2LatinRegexp3Chars.put("кӀы", "ćı");
        mCyrillic2LatinRegexp3Chars.put("кlы", "ćı");
        mCyrillic2LatinRegexp3Chars.put("куа", "ḱoá");

        mCyrillic2LatinRegexp3Chars.put("kӏу", "qu"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kiу", "qu"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӀу", "qu"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("klу", "qu"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӏо", "qo"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kiо", "qo"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӀо", "qo"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӏи", "ćiy"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kiи", "ćiy"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӀи", "ćiy"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӏы", "ćı"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kiы", "ćı"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kӀы", "ćı"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("klы", "ćı"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kуа", "ḱoá");// latin 'k'

        mCyrillic2LatinRegexp3Chars.put("шъу", "şü");
        mCyrillic2LatinRegexp3Chars.put("шъо", "şö");
        mCyrillic2LatinRegexp3Chars.put("шӏо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("шiо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("шӀо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("шӏу", "şü'ı");
        mCyrillic2LatinRegexp3Chars.put("шiу", "şü'ı");
        mCyrillic2LatinRegexp3Chars.put("шӀу", "şü'ı");
//        mCyrillic2LatinRegexp3Chars.put("шӏи", "ş'iy");
//        mCyrillic2LatinRegexp3Chars.put("шiи", "ş'iy");
//        mCyrillic2LatinRegexp3Chars.put("шӀи", "ş'iy");
        mCyrillic2LatinRegexp3Chars.put("шӏи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("шiи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("шӀи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("щъу", "şü");
        mCyrillic2LatinRegexp3Chars.put("щӏо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("щiо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("щӀо", "şü'e");
        mCyrillic2LatinRegexp3Chars.put("щӏу", "şü'ı");
        mCyrillic2LatinRegexp3Chars.put("щiу", "şü'ı");
        mCyrillic2LatinRegexp3Chars.put("щӀу", "şü'ı");
//        mCyrillic2LatinRegexp3Chars.put("щӏи", "ş'iy");
//        mCyrillic2LatinRegexp3Chars.put("щiи", "ş'iy");
//        mCyrillic2LatinRegexp3Chars.put("щӀи", "ş'iy");
        mCyrillic2LatinRegexp3Chars.put("щӏи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("щiи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("щӀи", "ş'i");
        mCyrillic2LatinRegexp3Chars.put("пӏу", "ṕu");
        mCyrillic2LatinRegexp3Chars.put("пiу", "ṕu");
        mCyrillic2LatinRegexp3Chars.put("пӀу", "ṕu");
        mCyrillic2LatinRegexp3Chars.put("хъу", "ḣu");
        mCyrillic2LatinRegexp3Chars.put("къу", "ku");
        mCyrillic2LatinRegexp3Chars.put("къo", "ko");

        mCyrillic2LatinRegexp3Chars.put("kъу", "ku"); // latin 'k'
        mCyrillic2LatinRegexp3Chars.put("kъo", "ko"); // latin 'k'

        mCyrillic2LatinRegexp3Chars.put("тӏу", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("тiу", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("тӀу", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("тIу", "ṫu");

        mCyrillic2LatinRegexp3Chars.put("tӏу", "ṫu"); // with Latin 't'
        mCyrillic2LatinRegexp3Chars.put("tiу", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("tӀу", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("tIу", "ṫu");

        mCyrillic2LatinRegexp3Chars.put("tӏy", "ṫu"); // with Latin 't' and 'y'
        mCyrillic2LatinRegexp3Chars.put("tiy", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("tӀy", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("tIy", "ṫu");

        mCyrillic2LatinRegexp3Chars.put("тӏy", "ṫu"); // with Latin 'y'
        mCyrillic2LatinRegexp3Chars.put("тiy", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("тӀy", "ṫu");
        mCyrillic2LatinRegexp3Chars.put("тIy", "ṫu");

        mCyrillic2LatinRegexp3Chars.put("дзу", "źu");
        mCyrillic2LatinRegexp3Chars.put("Iае", "aye");
        mCyrillic2LatinRegexp3Chars.put("ӏае", "aye");
        mCyrillic2LatinRegexp3Chars.put("iае", "aye");
//        mCyrillic2LatinRegexp3Chars.put("иIэ", "ie");
//        mCyrillic2LatinRegexp3Chars.put("иӏэ", "iye");
//        mCyrillic2LatinRegexp3Chars.put("иiэ", "iye");

        // 2 character letters
        mCyrillic2LatinRegexp2Chars.put("гу", "gu");
        mCyrillic2LatinRegexp2Chars.put("го", "go");
//        mCyrillic2LatinRegexp2Chars.put("га", "ga");
        mCyrillic2LatinRegexp2Chars.put("гъ", "ğ");
        mCyrillic2LatinRegexp2Chars.put("дж", "c");
        mCyrillic2LatinRegexp2Chars.put("дз", "ź");
        mCyrillic2LatinRegexp2Chars.put("жь", "j");
        mCyrillic2LatinRegexp2Chars.put("кӏ", "ć");
        mCyrillic2LatinRegexp2Chars.put("кi", "ć");
        mCyrillic2LatinRegexp2Chars.put("кӀ", "ć");
        mCyrillic2LatinRegexp2Chars.put("ку", "ḱu");
        mCyrillic2LatinRegexp2Chars.put("къ", "k");

        mCyrillic2LatinRegexp2Chars.put("kӏ", "ć"); // latin 'k'
        mCyrillic2LatinRegexp2Chars.put("ki", "ć"); // latin 'k'
        mCyrillic2LatinRegexp2Chars.put("kӀ", "ć"); // latin 'k'
        mCyrillic2LatinRegexp2Chars.put("kу", "ḱu"); // latin 'k'
        mCyrillic2LatinRegexp2Chars.put("kъ", "k"); // latin 'k'

        mCyrillic2LatinRegexp2Chars.put("лӏ", "ĺ'");
        mCyrillic2LatinRegexp2Chars.put("лi", "ĺ'");
        mCyrillic2LatinRegexp2Chars.put("лӀ", "ĺ'");
        mCyrillic2LatinRegexp2Chars.put("лъ", "ĺ");
        mCyrillic2LatinRegexp2Chars.put("шъ", "ş");
        mCyrillic2LatinRegexp2Chars.put("шӏ", "ş'");
        mCyrillic2LatinRegexp2Chars.put("шi", "ş'");
        mCyrillic2LatinRegexp2Chars.put("шӀ", "ş'");
        mCyrillic2LatinRegexp2Chars.put("щъ", "ş");
        mCyrillic2LatinRegexp2Chars.put("щӏ", "ş'");
        mCyrillic2LatinRegexp2Chars.put("щi", "ş'");
        mCyrillic2LatinRegexp2Chars.put("щӀ", "ş'");
        mCyrillic2LatinRegexp2Chars.put("пӏ", "ṕ");
        mCyrillic2LatinRegexp2Chars.put("пi", "ṕ");
        mCyrillic2LatinRegexp2Chars.put("пӀ", "ṕ");
        mCyrillic2LatinRegexp2Chars.put("тӏ", "ṫ");
        mCyrillic2LatinRegexp2Chars.put("тi", "ṫ");
        mCyrillic2LatinRegexp2Chars.put("тӀ", "ṫ");
        mCyrillic2LatinRegexp2Chars.put("тI", "ṫ");

        mCyrillic2LatinRegexp2Chars.put("tӏ", "ṫ"); // with Latin 't'
        mCyrillic2LatinRegexp2Chars.put("ti", "ṫ");
        mCyrillic2LatinRegexp2Chars.put("tӀ", "ṫ");
        mCyrillic2LatinRegexp2Chars.put("tI", "ṫ");

        mCyrillic2LatinRegexp2Chars.put("хъ", "ḣ");
        mCyrillic2LatinRegexp2Chars.put("хь", "h");
        mCyrillic2LatinRegexp2Chars.put("цӏ", "š");
        mCyrillic2LatinRegexp2Chars.put("цi", "š");
        mCyrillic2LatinRegexp2Chars.put("цӀ", "š");
        mCyrillic2LatinRegexp2Chars.put("жъ", "j");
//        mCyrillic2LatinRegexp2Char.put("цо", "ço");
        mCyrillic2LatinRegexp2Chars.put("цо", "çö");
        mCyrillic2LatinRegexp2Chars.put("цу", "çü");
        mCyrillic2LatinRegexp2Chars.put("чӏ", "ć");
        mCyrillic2LatinRegexp2Chars.put("чi", "ć");
        mCyrillic2LatinRegexp2Chars.put("чӀ", "ć");
        mCyrillic2LatinRegexp2Chars.put("чъ", "ç");
        mCyrillic2LatinRegexp2Chars.put("ӏу", "u");
        mCyrillic2LatinRegexp2Chars.put("iу", "u");
        mCyrillic2LatinRegexp2Chars.put("Ӏу", "u");
        mCyrillic2LatinRegexp2Chars.put("уэ", "we");
//        mCyrillic2LatinRegexp2Chars.put("уа", "wa");
//        mCyrillic2LatinRegexp2Chars.put("уа", "oá");
        mCyrillic2LatinRegexp2Chars.put("уу", "wıw");
        mCyrillic2LatinRegexp2Chars.put("ио", "yiwe");
        mCyrillic2LatinRegexp2Chars.put("ий", "yiy");
        mCyrillic2LatinRegexp2Chars.put("йи", "yiy");
        mCyrillic2LatinRegexp2Chars.put("иа", "yia");
        mCyrillic2LatinRegexp2Chars.put("иӀ", "yiı'");
//        mCyrillic2LatinRegexp2Chars.put("иӏ", "yiı'");
//        mCyrillic2LatinRegexp2Chars.put("иi", "yiı'");
//        mCyrillic2LatinRegexp2Chars.put("иу", "iwı");
        mCyrillic2LatinRegexp2Chars.put("йу", "ywı");
        mCyrillic2LatinRegexp2Chars.put("оо", "wewe");
        mCyrillic2LatinRegexp2Chars.put("ой", "wey");
        mCyrillic2LatinRegexp2Chars.put("ео", "yewe");
        mCyrillic2LatinRegexp2Chars.put("эи", "ey");
        mCyrillic2LatinRegexp2Chars.put("эй", "ey");
        mCyrillic2LatinRegexp2Chars.put("эе", "eye");
//        mCyrillic2LatinRegexp2Chars.put("эӀ", "e");
//        mCyrillic2LatinRegexp2Chars.put("эӏ", "e");
//        mCyrillic2LatinRegexp2Chars.put("эi", "e");
        mCyrillic2LatinRegexp2Chars.put("эо", "ewe");
        mCyrillic2LatinRegexp2Chars.put("ау", "aw");
        mCyrillic2LatinRegexp2Chars.put("эу", "ew");
        mCyrillic2LatinRegexp2Chars.put("ао", "awe");
        mCyrillic2LatinRegexp2Chars.put("ае", "aye");
//        mCyrillic2LatinRegexp2Chars.put("аи", "ayı");
        mCyrillic2LatinRegexp2Chars.put("ыо", "ıwe");
        mCyrillic2LatinRegexp2Chars.put("ые", "ıye");
        mCyrillic2LatinRegexp2Chars.put("ый", "iy");
        mCyrillic2LatinRegexp2Chars.put("ӏэ", "e");
        mCyrillic2LatinRegexp2Chars.put("iэ", "e");
        mCyrillic2LatinRegexp2Chars.put("Ӏэ", "e");
        mCyrillic2LatinRegexp2Chars.put("ӏа", "a");
        mCyrillic2LatinRegexp2Chars.put("iа", "a");
        mCyrillic2LatinRegexp2Chars.put("Ӏа", "a");
        mCyrillic2LatinRegexp2Chars.put("Iо", "o");
        mCyrillic2LatinRegexp2Chars.put("ӏо", "o");
        mCyrillic2LatinRegexp2Chars.put("iо", "o");
        mCyrillic2LatinRegexp2Chars.put("Iи", "i");
        mCyrillic2LatinRegexp2Chars.put("ӏи", "i");
        mCyrillic2LatinRegexp2Chars.put("iи", "i");

        mCyrillic2LatinRegexp2Chars.put("ыи", "ıyı");
//        mCyrillic2LatinRegexp2Chars.put("ыу\\S", "ıwı");
        mCyrillic2LatinRegexp2Chars.put("ыу\\S", "ıwı");

        // prefixes 1-char
        mCyrillic2LatinRegexpPrefixes1Char.put("^е", "ye");
        mCyrillic2LatinRegexpPrefixes1Char.put("^у[^эаеи]", "wı");
        mCyrillic2LatinRegexpPrefixes1Char.put("^и", "yi");
//        mCyrillic2LatinRegexpPrefixes1Char.put("^и", "i");
        mCyrillic2LatinRegexpPrefixes1Char.put("^о", "we");

        // prefixes 2-chars
        mCyrillic2LatinRegexpPrefixes2Chars.put("^Iо", "o");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^ӏо", "o");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^iо", "o");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^ио", "yiwe");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^иI", "yiı'");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^иӏ", "yiı'");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^иi", "yiı'");
        mCyrillic2LatinRegexpPrefixes2Chars.put("^уу", "wıw");

        // prefixes 3-chars
//        mCyrillic2LatinRegexpPrefixes3Chars.put("^иIэ", "iye");
//        mCyrillic2LatinRegexpPrefixes3Chars.put("^иӏэ", "iye");
//        mCyrillic2LatinRegexpPrefixes3Chars.put("^иiэ", "iye");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^иIэ", "yie");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^иӏэ", "yie");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^иiэ", "yie");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^Iаe", "aye");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^ӏаe", "aye");
        mCyrillic2LatinRegexpPrefixes3Chars.put("^iаe", "aye");

        // postfixes
//        mCyrillic2LatinRegexpPostfixes.put("ыу$", "ıw");

        // single character letters
        mCyrillic2LatinSingleCharMap.put("а", "a");
        mCyrillic2LatinSingleCharMap.put("б", "b");
        mCyrillic2LatinSingleCharMap.put("в", "v");
        mCyrillic2LatinSingleCharMap.put("г", "ǵ");
        mCyrillic2LatinSingleCharMap.put("д", "d");
        mCyrillic2LatinSingleCharMap.put("e", "é");
        mCyrillic2LatinSingleCharMap.put("е", "é");
        mCyrillic2LatinSingleCharMap.put("ж", "j");
        mCyrillic2LatinSingleCharMap.put("з", "z");
        mCyrillic2LatinSingleCharMap.put("и", "i");
        mCyrillic2LatinSingleCharMap.put("й", "y");
        mCyrillic2LatinSingleCharMap.put("к", "ḱ");

        mCyrillic2LatinSingleCharMap.put("k", "ḱ"); // latin 'k'

        mCyrillic2LatinSingleCharMap.put("л", "l");
        mCyrillic2LatinSingleCharMap.put("м", "m");
        mCyrillic2LatinSingleCharMap.put("н", "n");
        mCyrillic2LatinSingleCharMap.put("о", "o");

        mCyrillic2LatinSingleCharMap.put("п", "p");
        mCyrillic2LatinSingleCharMap.put("р", "r");
        mCyrillic2LatinSingleCharMap.put("p", "r"); // English letter "p"
        mCyrillic2LatinSingleCharMap.put("c", "с"); // English letter "c"
        mCyrillic2LatinSingleCharMap.put("с", "s");
        mCyrillic2LatinSingleCharMap.put("т", "t");
        mCyrillic2LatinSingleCharMap.put("у", "u");
//        mCyrillic2LatinSingleCharMap.put("у", "w");
        mCyrillic2LatinSingleCharMap.put("ф", "f");
        mCyrillic2LatinSingleCharMap.put("х", "x");
        mCyrillic2LatinSingleCharMap.put("ц", "ś");
        mCyrillic2LatinSingleCharMap.put("ч", "ç");
        mCyrillic2LatinSingleCharMap.put("ш", "ş");
        mCyrillic2LatinSingleCharMap.put("щ", "ş"); // TODO ???
//        mCyrillic2LatinSingleCharMap.put("ъ", ""); // TODO ???
        mCyrillic2LatinSingleCharMap.put("ы", "ı");

        mCyrillic2LatinSingleCharMap.put("ь", ""); // TODO ???
        mCyrillic2LatinSingleCharMap.put("э", "e");
        mCyrillic2LatinSingleCharMap.put("ю", "yu");
        mCyrillic2LatinSingleCharMap.put("я", "ya");

        //========================================================
        //========= Latin-to-Cyrillic =========
        //========================================================
        // 4 character letters
        mLatin2CyrillicRegexp4Chars.put("şü'e", "шӀо");
        mLatin2CyrillicRegexp4Chars.put("şü'ı", "шӀу");

        // 3 character letters
//        mLatin2CyrillicRegexp3Chars.put("abc", "def");

        // 2 character letters
        // a, á, i, ı, y, u, ü, o, ö, e, é, '
        //mLatin2CyrillicRegexp2Chars.put("a", "а"); // prefixed
//        mLatin2CyrillicRegexp2Chars.put("s", "с");
//        mLatin2CyrillicRegexp2Chars.put("d", "д");
//        mLatin2CyrillicRegexp2Chars.put("f", "ф");
//        mLatin2CyrillicRegexp2Chars.put("g", "г");
//        mLatin2CyrillicRegexp2Chars.put("h", "хь");
        mLatin2CyrillicRegexp2Chars.put("jü", "жъу");
        mLatin2CyrillicRegexp2Chars.put("jö", "жъо");

        mLatin2CyrillicRegexp2Chars.put("wı", "у");

//        mLatin2CyrillicRegexp2Chars.put("we", "уэ");
        mLatin2CyrillicRegexp2Chars.put("we", "о");

        mLatin2CyrillicRegexp2Chars.put("ya", "я");
        mLatin2CyrillicRegexp2Chars.put("yu", "ю");
        mLatin2CyrillicRegexp2Chars.put("ye", "е");

        mLatin2CyrillicRegexp2Chars.put("oá", "уа");

//        mLatin2CyrillicRegexp2Chars.put("k", "къ");
//        mLatin2CyrillicRegexp2Chars.put("l", "л");
        //mLatin2CyrillicRegexp2Chars.put("i", "и"); // prefixed
//        mLatin2CyrillicRegexp2Chars.put("q", "кӀ");
//        mLatin2CyrillicRegexp2Chars.put("w", "у");
        //mLatin2CyrillicRegexp2Chars.put("e", "э"); // prefixed
//        mLatin2CyrillicRegexp2Chars.put("r", "р");
//        mLatin2CyrillicRegexp2Chars.put("t", "т");
//        mLatin2CyrillicRegexp2Chars.put("y", "й"); // ??
//        mLatin2CyrillicRegexp2Chars.put("u", "у"); // ??
//        mLatin2CyrillicRegexp2Chars.put("o", "о"); // ??
//        mLatin2CyrillicRegexp2Chars.put("p", "п");
//        mLatin2CyrillicRegexp2Chars.put("z", "з");
//        mLatin2CyrillicRegexp2Chars.put("x", "х");
//        mLatin2CyrillicRegexp2Chars.put("c", "дж");
//        mLatin2CyrillicRegexp2Chars.put("v", "в");
//        mLatin2CyrillicRegexp2Chars.put("b", "б");
//        mLatin2CyrillicRegexp2Chars.put("n", "н");
//        mLatin2CyrillicRegexp2Chars.put("m", "м");
//        mLatin2CyrillicRegexp2Chars.put("á", "а"); // ??
        mLatin2CyrillicRegexp2Chars.put("çö", "цо");
        mLatin2CyrillicRegexp2Chars.put("çü", "цу");
//        mLatin2CyrillicRegexp2Chars.put("ć", "чӀ");
        //mLatin2CyrillicRegexp2Chars.put("é", "е"); // ??
//        mLatin2CyrillicRegexp2Chars.put("ǵ", "г");
//        mLatin2CyrillicRegexp2Chars.put("ğ", "гъ");
//        mLatin2CyrillicRegexp2Chars.put("ḣ", "хъ");
        //mLatin2CyrillicRegexp2Chars.put("ı", "ы"); // ??
//        mLatin2CyrillicRegexp2Chars.put("ḱ", "к");

        //YYY111
        // Ӏ <===== Cyrillic from gboard
        // I <===== I from English
        // Ӏ <===== unknown??

        mLatin2CyrillicRegexp2Chars.put("ĺ'", "лӀ");
        //mLatin2CyrillicRegexp2Chars.put("ö", "о"); // ??
//        mLatin2CyrillicRegexp2Chars.put("ṕ", "пӀ");
        mLatin2CyrillicRegexp2Chars.put("şü", "шъу");
        mLatin2CyrillicRegexp2Chars.put("şö", "шъо");
        mLatin2CyrillicRegexp2Chars.put("ş'", "шӀ");
        mLatin2CyrillicRegexp2Chars.put("ı'", "Ӏ");
//        mLatin2CyrillicRegexp2Chars.put("ś", "ц");
//        mLatin2CyrillicRegexp2Chars.put("š", "цӀ");
//        mLatin2CyrillicRegexp2Chars.put("ṫ", "тӀ");
        //mLatin2CyrillicRegexp2Chars.put("ü", "u"); // ??
//        mLatin2CyrillicRegexp2Chars.put("ź", "дз");

        /*
        а б в г д е ё ж з и й к л м н о   п р с т у ф х ц ч ш щ ъ ы ь э ю я
        гъу жъу кӀу шъу шӀу пӀу хъу къу тӀу дзу
        гу гъ дж дз жь кӀ ку къ лӀ лъ шъ пӀ тӀ хъ хь цӀ жъ цу чӀ чЪ шӀ Ӏу

        áçćéǵğḣıḱĺöṕşśšṫüź
        ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
        asdfghjkliqwertyuopzxcvbnm
        */

        // prefixes 1-char
        mLatin2CyrillicRegexpPrefixes1Char.put("^ı", "ы");
//        mLatin2CyrillicRegexpPrefixes1Char.put("^e", "Ӏэ"); // TODO ewe : эо vs. ebaće / ӀэбакӀэ
        mLatin2CyrillicRegexpPrefixes1Char.put("^e", "э"); // TODO ewe : эо vs. ebaće / ӀэбакӀэ
//        mLatin2CyrillicRegexpPrefixes1Char.put("^a", "Ӏа"); //TODO aye / Ӏаe and ane / Ӏанэ vs. Adıǵe / Адыгэ

        mLatin2CyrillicRegexpPrefixes1Char.put("^o", "Ӏо");

        // prefixes 2-chars
        mLatin2CyrillicRegexpPrefixes2Chars.put("^yi", "и");
        mLatin2CyrillicRegexpPrefixes2Chars.put("^ye", "е");

        // prefixes 3-chars
//        mLatin2CyrillicRegexpPrefixes3Chars.put("^abc", "def");

        // postfixes
//        mLatin2CyrillicRegexpPostfixes.put("ıw$", "ыу");

        // single character letters
        mLatin2CyrillicSingleCharMap.put("'", "Ӏ");
        mLatin2CyrillicSingleCharMap.put("a", "а");
        mLatin2CyrillicSingleCharMap.put("s", "с");
        mLatin2CyrillicSingleCharMap.put("d", "д");
        mLatin2CyrillicSingleCharMap.put("f", "ф");
        mLatin2CyrillicSingleCharMap.put("g", "г");
        mLatin2CyrillicSingleCharMap.put("h", "хь");
        mLatin2CyrillicSingleCharMap.put("j", "ж");
        mLatin2CyrillicSingleCharMap.put("k", "къ");
        mLatin2CyrillicSingleCharMap.put("l", "л");
        mLatin2CyrillicSingleCharMap.put("i", "и");
        mLatin2CyrillicSingleCharMap.put("q", "кӀ");
        mLatin2CyrillicSingleCharMap.put("w", "у");
        mLatin2CyrillicSingleCharMap.put("e", "э");
        mLatin2CyrillicSingleCharMap.put("r", "р");
        mLatin2CyrillicSingleCharMap.put("t", "т");
        mLatin2CyrillicSingleCharMap.put("y", "й");
        mLatin2CyrillicSingleCharMap.put("u", "у");
        mLatin2CyrillicSingleCharMap.put("o", "о");
        mLatin2CyrillicSingleCharMap.put("p", "п");
        mLatin2CyrillicSingleCharMap.put("z", "з");
        mLatin2CyrillicSingleCharMap.put("x", "х");
        mLatin2CyrillicSingleCharMap.put("c", "дж");
        mLatin2CyrillicSingleCharMap.put("v", "в");
        mLatin2CyrillicSingleCharMap.put("b", "б");
        mLatin2CyrillicSingleCharMap.put("n", "н");
        mLatin2CyrillicSingleCharMap.put("m", "м");
        mLatin2CyrillicSingleCharMap.put("á", "а"); //<== TODO
        mLatin2CyrillicSingleCharMap.put("ç", "ч");
//        mLatin2CyrillicSingleCharMap.put("ć", "чӀ"); // TODO ćıgu / чӀыгу vs. ćiy / кӀи and ebaće / ӀэбакӀэ and ćenće
        mLatin2CyrillicSingleCharMap.put("ć", "кӀ"); // TODO apparently "кӀ" is used far more than "чӀ" so making it default.
        mLatin2CyrillicSingleCharMap.put("é", "е");
        mLatin2CyrillicSingleCharMap.put("ǵ", "г");
        mLatin2CyrillicSingleCharMap.put("ğ", "гъ");
        mLatin2CyrillicSingleCharMap.put("ḣ", "хъ");
        mLatin2CyrillicSingleCharMap.put("ı", "ы");
        mLatin2CyrillicSingleCharMap.put("ḱ", "к");
        mLatin2CyrillicSingleCharMap.put("ĺ", "лъ");
        mLatin2CyrillicSingleCharMap.put("ö", "о"); //<== TODO
        mLatin2CyrillicSingleCharMap.put("ṕ", "пӀ");
        mLatin2CyrillicSingleCharMap.put("ş", "ш");
        mLatin2CyrillicSingleCharMap.put("ś", "ц");
        mLatin2CyrillicSingleCharMap.put("š", "цӀ");
        mLatin2CyrillicSingleCharMap.put("ṫ", "тӀ");
        mLatin2CyrillicSingleCharMap.put("ü", "u"); //<== TODO
        mLatin2CyrillicSingleCharMap.put("ź", "дз");

//        áçćéǵğḣıḱĺöṕşśšṫüź
//        гу гъ дж дз жь кӀ ку къ лӀ лъ шъ пӀ тӀ хъ хь цӀ жъ цу чӀ чЪ шӀ Ӏу
    }

    public String toLowerCase(String str, boolean isLatin, boolean isCyrillic) {
        if (isLatin && isCyrillic) {
            Log.e(TAG, "BUG toLowerCase(): isLatin == true && isCyrillic == true");
        }
        final int len = str.length();
        if (len == 0) {
            return str;
        }
        if (!isLatin && !isCyrillic) {
            return str.toLowerCase();
        }
        String newStr = "";
        for (int i =0; i < len; ++i) {
            String currentChar = str.substring(i, i + 1);
            if (isLatin) {
                if (currentChar.equals("i") || currentChar.equals("İ")) {
                    currentChar = "i";
                } else if (currentChar.equals("ı") || currentChar.equals("I")) {
                    currentChar = "ı";
                    // next one is to deal with the "`" character which may be used by mistake instead of the correct character "'"
                } else if (currentChar.equals("'") || currentChar.equals("`") || currentChar.equals("’")) {
                    currentChar = "'";
                } else {
                    currentChar = currentChar.toLowerCase();
                }
            } else {
                if (currentChar.equals("I") || currentChar.equals("i") || currentChar.equals("Ӏ") || currentChar.equals("ı") || currentChar.equals("İ") || currentChar.equals("l")) {
                    currentChar = "Ӏ";
                    // next one is to deal with the "`" character which may be used by mistake instead of the correct character "'"
                } else if (currentChar.equals("'") || currentChar.equals("`") || currentChar.equals("’")) {
                    currentChar = "'";
                } else {
                    currentChar = currentChar.toLowerCase();
                }
            }
            newStr = newStr + currentChar;
        }
        return newStr;
    }

    public String toUpperCase(String str, boolean isLatin, boolean isCyrillic) {
        if (isLatin && isCyrillic) {
            Log.e(TAG, "BUG toLowerCase(): isLatin == true && isCyrillic == true");
        }
        final int len = str.length();
        if (len == 0) {
            return str;
        }
        if (!isLatin && !isCyrillic) {
            return str.toUpperCase();
        }
        String newStr = "";
        for (int i =0; i < len; ++i) {
            String currentChar = str.substring(i, i + 1);
            if (isLatin) {
                if (currentChar.equals("i") || currentChar.equals("İ")) {
                    currentChar = "İ";
                } else if (currentChar.equals("ı") || currentChar.equals("I")) {
                    currentChar = "I";
                    // next one is to deal with the "`" character which may be used by mistake instead of the correct character "'"
                } else if (currentChar.equals("'") || currentChar.equals("`") || currentChar.equals("’")) {
                    currentChar = "'";
                } else {
                    currentChar = currentChar.toUpperCase();
                }
            } else if (isCyrillic) {
                if (currentChar.equals("I") || currentChar.equals("i") || currentChar.equals("Ӏ") || currentChar.equals("ı") || currentChar.equals("İ") || currentChar.equals("l")) {
                    currentChar = "Ӏ";
                    // next one is to deal with the "`" character which may be used by mistake instead of the correct character "'"
                } else if (currentChar.equals("'") || currentChar.equals("`") || currentChar.equals("’")) {
                    currentChar = "'";
                } else {
                    currentChar = currentChar.toUpperCase();
                }
            }
            newStr = newStr + currentChar;
        }
        return newStr;
    }

    private String specialCharToEnglishChar(String str) {
        String convertedStr = mSpecial2EnglishCharacterMap.get(str);
        if (convertedStr == null) {
            return str;
        } else {
            return convertedStr;
        }
    }

    public String specialCharsToEnglishChars(String str) {
        String convertedStr = "";
        final int len = str.length();
        for (int i =0; i < len; ++i) {
            String currentChar = str.substring(i, i + 1);
            convertedStr += specialCharToEnglishChar(currentChar);
        }
        return convertedStr;
    }

    private String cyrillicExtraCharToBasicChar(String str) {
        String convertedStr = mCyrillicExtra2BasicCharacterMap.get(str);
        if (convertedStr == null) {
            return str;
        } else {
            return convertedStr;
        }
    }

    public String cyrillicExtraCharsToBasicChars(String str) {
        String convertedStr = "";
        final int len = str.length();
        for (int i =0; i < len; ++i) {
            String currentChar = str.substring(i, i + 1);
            convertedStr += cyrillicExtraCharToBasicChar(currentChar);
        }
        return convertedStr;
    }

    public String capitalize(String str, boolean isLatin, boolean isCyrillic) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return toUpperCase(str.substring(0, 1), isLatin, isCyrillic) + toLowerCase(str.substring(1), isLatin, isCyrillic);
    }

    public String formatString(String str, String patternToHighlight, boolean searchStrictWithSpecialChars, String defaultColorCode, String highlightColorCode, boolean isLatin, boolean isCyrillic) {
        final int lenOfStrToHighlight = patternToHighlight.length();
        final int lenOfStr = str.length();
        if (lenOfStr < lenOfStrToHighlight) {
            Log.e(TAG, "BUG substring to be highlighted is longer than the word itself!: " + patternToHighlight + " / " + str);
            return str;
        }
        String convertedStr = toLowerCase(str, isLatin, isCyrillic);
        if (!searchStrictWithSpecialChars) {
            convertedStr = specialCharsToEnglishChars(convertedStr);
        }
        // TODO to improve performance use the cyrillicExtraCharsToBasicChars() call (everywhere it is being used) only if text is cyrillic
        convertedStr = cyrillicExtraCharsToBasicChars(convertedStr);

        if (!convertedStr.contains(patternToHighlight)) {
            return str;
        }
        int highlightingStartIndex = convertedStr.indexOf(patternToHighlight);
        int highlightingEndIndex = highlightingStartIndex + lenOfStrToHighlight;
        String strToHighlight = str.substring(highlightingStartIndex, highlightingEndIndex);
        String highlightedStr;
        highlightedStr = "<font color=" + highlightColorCode + ">" + strToHighlight + "</font>";
        String formattedStr = "";
        String strLeftToSearch = str;
        int indexOfStrToHighlight = strLeftToSearch.indexOf(strToHighlight);
        while(indexOfStrToHighlight != -1) {
            if (indexOfStrToHighlight == 0) {
                formattedStr += highlightedStr;
                strLeftToSearch = strLeftToSearch.substring(lenOfStrToHighlight);
            } else {
                formattedStr += "<font color=" + defaultColorCode + ">" + strLeftToSearch.substring(0, indexOfStrToHighlight) + "</font>" + highlightedStr;
                strLeftToSearch = strLeftToSearch.substring(indexOfStrToHighlight + lenOfStrToHighlight);
            }
            if (strLeftToSearch.isEmpty()) {
                break;
            }
            indexOfStrToHighlight = strLeftToSearch.indexOf(strToHighlight);
            if (indexOfStrToHighlight == -1) {
                formattedStr += "<font color=" + defaultColorCode + ">" + strLeftToSearch + "</font>";
                break;
            }
        }
        return formattedStr;
    }

    public String highlightNonCyrillicChars(String str, String defaultColorCode, String highlightColorCode) {
        final int lenOfStr = str.length();
        if (lenOfStr < 1) {
            Log.e(TAG, "BUG highlightNonCyrillicChars(): str is empty");
            return str;
        }
        boolean inHighlightedSegment = false;
        String formattedStr = "";
        for (int i = 0; i < lenOfStr; ++i) {
            final String currentChar = str.substring(i, i+1);
            final boolean isWhitespace = (currentChar.trim().length() == 0);
            if (!mCyrillicAllowedChars.contains(currentChar) && !isWhitespace) {
                // this char is not in the supported Cyrillic chars list
                if (!inHighlightedSegment) {
                    inHighlightedSegment = true;
                    if (i == 0) {
                        formattedStr = "<font color=" + highlightColorCode + ">" + currentChar;
                    } else {
                        formattedStr = formattedStr + "</font><font color=" + highlightColorCode + ">" + currentChar;
                    }
                } else {
                    formattedStr = formattedStr + currentChar;
                }
                Log.d(TAG, "YYY314 unsupported Cyrillic character: " + currentChar);
            } else {
                if (inHighlightedSegment) {
                    inHighlightedSegment = false;
                    formattedStr = formattedStr + "</font><font color=" + defaultColorCode + ">" + currentChar;
                } else {
                    if (i == 0) {
                        formattedStr = "<font color=" + defaultColorCode + ">" + currentChar;
                    } else {
                        formattedStr = formattedStr + currentChar;
                    }
                }
            }
        }
        formattedStr = formattedStr + "</font>";
        return formattedStr;
    }

    public String cyrillic2Latin(String str) {
        List<String> tokensAndSpaces = new ArrayList();
        String currentToken = "";
        boolean spaces = true;
        // DONE TODO replace split with a fix due to Android 11 adding an empty character at the beginning
        for (int index = 0; index < str.length(); ++index) {
            final String singleChar = str.substring(index, index+1);
            if (singleChar.isEmpty()) {
                continue;
            }
            if (singleChar.trim().length() == 0) {
                if (spaces) {
                    currentToken = currentToken + singleChar;
                } else {
                    spaces = true;
                    if (!currentToken.isEmpty()) {
                        tokensAndSpaces.add(currentToken);
                    }
                    currentToken = singleChar;
                }
            } else {
                if (spaces) {
                    spaces = false;
                    if (!currentToken.isEmpty()) {
                        tokensAndSpaces.add(currentToken);
                    }
                    currentToken = singleChar;
                } else {
                    currentToken = currentToken + singleChar;
                }
            }
        }
        if (!currentToken.isEmpty()) {
            tokensAndSpaces.add(currentToken);
        }

        String result = "";
        for (String token : tokensAndSpaces) {
            result = result + cyrillic2LatinSingleWord(token);
        }
        return result;
    }

    private String cyrillic2LatinSingleWord(String str) {
        final String toInspect = toLowerCase("", false, true);
//        final String toInspect = toLowerCase("шIын", false);
//        Log.d(TAG, "YYY8 str, toInspect: " + str + ", " + toInspect);
        final int lenCyrillic = str.length();
        if (lenCyrillic == 0) {
            return "";
        }
        String origStr = str;
        str = toLowerCase(str, false, true);
        String newStr = "";

        boolean inspect = false;
        if (str.equals(toInspect)) {
            inspect = true;
        }
        for (int i = 0; i < lenCyrillic;) {
            String currentChar = str.substring(i);
            String currentCharOrig = origStr.substring(i);
            boolean isUpper = originalIsUpperCase(currentCharOrig, false, true);
            boolean matched = false;
            if (i == 0) {
                // 3 char prefixes
                for (String regex : mCyrillic2LatinRegexpPrefixes3Chars.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_3 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_3 prefix matched: " + regex + ", " + mCyrillic2LatinRegexpPrefixes3Chars.get(regex));
                        }
                        i = i + 3;
                        matched = true;
                        String convertedChar = mCyrillic2LatinRegexpPrefixes3Chars.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, true, false);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenCyrillic) {
                    break;
                }
                if (matched) {
                    continue;
                }
                // 2 char prefixes
                for (String regex : mCyrillic2LatinRegexpPrefixes2Chars.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_2 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_2 prefix matched: " + regex + ", " + mCyrillic2LatinRegexpPrefixes2Chars.get(regex));
                        }
                        i = i + 2;
                        matched = true;
                        String convertedChar = mCyrillic2LatinRegexpPrefixes2Chars.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, true, false);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenCyrillic) {
                    break;
                }
                if (matched) {
                    continue;
                }
                // 1 char prefixes
                for (String regex : mCyrillic2LatinRegexpPrefixes1Char.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_1 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_1 prefix matched: " + regex + ", " + mCyrillic2LatinRegexpPrefixes1Char.get(regex));
                        }
                        i = i + 1;
                        matched = true;
                        String convertedChar = mCyrillic2LatinRegexpPrefixes1Char.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, true, false);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenCyrillic) {
                    break;
                }
                if (matched) {
                    continue;
                }
            }

            // 4-chararcter compounds
            for (String regex : mCyrillic2LatinRegexp4Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b4 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c4 matched: " + regex + ", " + mCyrillic2LatinRegexp4Chars.get(regex));
                    }
                    i = i + 4;
                    matched = true;
                    String convertedChar = mCyrillic2LatinRegexp4Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, true, false);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenCyrillic) {
                break;
            }
            if (matched) {
                continue;
            }
            // 3-chararcter compounds
            for (String regex : mCyrillic2LatinRegexp3Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b3 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c3 matched: " + regex + ", " + mCyrillic2LatinRegexp3Chars.get(regex));
                    }
                    i = i + 3;
                    matched = true;
                    String convertedChar = mCyrillic2LatinRegexp3Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, true, false);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenCyrillic) {
                break;
            }
            if (matched) {
                continue;
            }
            // 2-chararcter compounds
            for (String regex : mCyrillic2LatinRegexp2Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b2 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c2 matched: " + regex + ", " + mCyrillic2LatinRegexp2Chars.get(regex));
                    }
                    i = i + 2;
                    matched = true;
                    String convertedChar = mCyrillic2LatinRegexp2Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, true, false);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenCyrillic) {
                break;
            }
            if (matched) {
                continue;
            }
            currentChar = str.substring(i, i + 1);
            currentCharOrig = origStr.substring(i, i + 1);
            isUpper = originalIsUpperCase(currentCharOrig, false, true);
            // 1-chararcters
            for (String c : mCyrillic2LatinSingleCharMap.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b1 c: " + c);
                }
                if (currentChar.matches(c)) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c1 matched: " + c + ", " + mCyrillic2LatinSingleCharMap.get(c));
                    }
                    matched = true;
                    String convertedChar = mCyrillic2LatinSingleCharMap.get(c);
                    if (inspect) {
                        Log.d(TAG, "YYY8-- currentChar: " + currentChar);
                    }
                    if (i >  0) {
                        final String withPrevChar = str.substring(i - 1, i + 1);
                        if (withPrevChar.equals("оо")) {
                            convertedChar = "we";
                        } else if (withPrevChar.equals("еу")) {
                            convertedChar = "w";
                        } else if (withPrevChar.equals("ыу")) {
                            convertedChar = "w";
                        } else if (withPrevChar.equals("иу")) {
                            convertedChar = "w";
                        } else if (currentChar.equals("у")) {
                            if ((i + 1) == lenCyrillic) {
                                convertedChar = "u'";
                            }
                        } else if (withPrevChar.equals("аи")) {
                            convertedChar = "yı";
                            if ((i + 1) == lenCyrillic) {
                                convertedChar = "y";
                            }
                        }
                    }
                    i = i + 1;
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, true, false);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenCyrillic) {
                break;
            }
            if (matched) {
                continue;
            }
            if (inspect) {
                Log.d(TAG, "YYY8c5 not matched: " + currentChar);
            }
            // no match
            newStr = newStr + currentCharOrig;
            i = i + currentCharOrig.length();
        }
        return newStr;
    }

    public String latin2Cyrillic(String str) {
        List<String> tokensAndSpaces = new ArrayList();
        String currentToken = "";
        boolean spaces = true;
        // DONE TODO replace split with a fix due to Android 11 adding an empty character at the beginning
        for (int index = 0; index < str.length(); ++index) {
            final String singleChar = str.substring(index, index+1);
            if (singleChar.isEmpty()) {
                continue;
            }
            if (singleChar.trim().length() == 0) {
                if (spaces) {
                    currentToken = currentToken + singleChar;
                } else {
                    spaces = true;
                    if (!currentToken.isEmpty()) {
                        tokensAndSpaces.add(currentToken);
                    }
                    currentToken = singleChar;
                }
            } else {
                if (spaces) {
                    spaces = false;
                    if (!currentToken.isEmpty()) {
                        tokensAndSpaces.add(currentToken);
                    }
                    currentToken = singleChar;
                } else {
                    currentToken = currentToken + singleChar;
                }
            }
        }
        if (!currentToken.isEmpty()) {
            tokensAndSpaces.add(currentToken);
        }

        String result = "";
        for (String token : tokensAndSpaces) {
            result = result + latin2CyrillicSingleWord(token);
        }
        return result;
    }

    private String latin2CyrillicSingleWord(String str) {
//        final String toInspect = "пхъэнтıэкıу";
        final String toInspect = "";
        final int lenLatin = str.length();
        if (lenLatin == 0) {
            return "";
        }
        String origStr = str;
        str = toLowerCase(str, true, false);
        String newStr = "";
        boolean inspect = false;
        if (str.equals(toInspect)) {
            inspect = true;
        }
        for (int i = 0; i < lenLatin;) {
            String currentChar = str.substring(i);
            String currentCharOrig = origStr.substring(i);
            boolean isUpper = originalIsUpperCase(currentCharOrig, true, false);
            boolean matched = false;
            if (i == 0) {
                // TODO remove all unneeded hash maps (3-chars and maybe others)
                // 3 char prefixes
                for (String regex : mLatin2CyrillicRegexpPrefixes3Chars.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_3 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_3 prefix matched: " + regex + ", " + mLatin2CyrillicRegexpPrefixes3Chars.get(regex));
                        }
                        i = i + 3;
                        matched = true;
                        String convertedChar = mLatin2CyrillicRegexpPrefixes3Chars.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, false, true);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenLatin) {
                    break;
                }
                if (matched) {
                    continue;
                }
                // 2 char prefixes
                for (String regex : mLatin2CyrillicRegexpPrefixes2Chars.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_2 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_2 prefix matched: " + regex + ", " + mLatin2CyrillicRegexpPrefixes2Chars.get(regex));
                        }
//                        i = i + 2;
                        i = i + regex.length() - 1;
                        matched = true;
                        String convertedChar = mLatin2CyrillicRegexpPrefixes2Chars.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, false, true);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenLatin) {
                    break;
                }
                if (matched) {
                    continue;
                }
                // 1 char prefixes
                for (String regex : mLatin2CyrillicRegexpPrefixes1Char.keySet()) {
                    if (inspect) {
                        Log.d(TAG, "YYY8b6_1 prefix currentChar, regex: " + currentChar + ", " + regex);
                    }
                    if (currentChar.matches(regex + "\\S*")) {
                        if (inspect) {
                            Log.d(TAG, "YYY8c6_1 prefix matched: " + regex + ", " + mLatin2CyrillicRegexpPrefixes1Char.get(regex));
                        }
                        i = i + 1;
                        matched = true;
                        String convertedChar = mLatin2CyrillicRegexpPrefixes1Char.get(regex);
                        if (isUpper) {
                            convertedChar = capitalize(convertedChar, false, true);
                        }
                        newStr = newStr + convertedChar;
                        break;
                    }
                }
                if (i >= lenLatin) {
                    break;
                }
                if (matched) {
                    continue;
                }
            }

            // 4-chararcter compounds
            for (String regex : mLatin2CyrillicRegexp4Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b4 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c4 matched: " + regex + ", " + mLatin2CyrillicRegexp4Chars.get(regex));
                    }
                    i = i + 4;
                    matched = true;
                    String convertedChar = mLatin2CyrillicRegexp4Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, false, true);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenLatin) {
                break;
            }
            if (matched) {
                continue;
            }
            // 3-chararcter compounds
            for (String regex : mLatin2CyrillicRegexp3Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b3 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c3 matched: " + regex + ", " + mLatin2CyrillicRegexp3Chars.get(regex));
                    }
                    i = i + 3;
                    matched = true;
                    String convertedChar = mLatin2CyrillicRegexp3Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, false, true);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenLatin) {
                break;
            }
            if (matched) {
                continue;
            }
            // 2-chararcter compounds
            for (String regex : mLatin2CyrillicRegexp2Chars.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b2 currentChar, regex: " + currentChar + ", " + regex);
                }
                if (currentChar.matches("^" + regex + "\\S*")) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c2 matched: " + regex + ", " + mLatin2CyrillicRegexp2Chars.get(regex));
                    }
                    i = i + 2;
                    matched = true;
                    String convertedChar = mLatin2CyrillicRegexp2Chars.get(regex);
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, false, true);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenLatin) {
                break;
            }
            if (matched) {
                continue;
            }
            currentChar = str.substring(i, i + 1);
            currentCharOrig = origStr.substring(i, i + 1);
            isUpper = originalIsUpperCase(currentCharOrig, true, false);
            // 1-chararcters
            for (String c : mLatin2CyrillicSingleCharMap.keySet()) {
                if (inspect) {
                    Log.d(TAG, "YYY8b1 c: " + c);
                }
                if (currentChar.matches(c)) {
                    if (inspect) {
                        Log.d(TAG, "YYY8c1 matched: " + c + ", " + mLatin2CyrillicSingleCharMap.get(c));
                    }
                    matched = true;
                    String convertedChar = mLatin2CyrillicSingleCharMap.get(c);
                    if (inspect) {
                        Log.d(TAG, "YYY8-- currentChar: " + currentChar);
                    }
                    if (i >  0) {
                        final String withPrevChar = str.substring(i - 1, i + 1);
                        //ĺeu лъэӀу
                        //yiex иӀэx
                        /*
                        а б в г д е ё ж з и й к л м н о   п р с т у ф х ц ч ш щ ъ ы ь э ю я
                        гъу жъу кӀу шъу шӀу пӀу хъу къу тıу дзу
                        гу гъ дж дз жь кӀ ку къ лӀ лъ шъ пӀ тӀ хъ хь цӀ жъ цу чӀ чЪ шӀ Ӏу

                        áçćéǵğḣıḱĺöṕşśšṫüź
                        ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ
                        asdfghjkliqwertyuopzxcvbnm
                        */
                        if (withPrevChar.equals("eu")) {
                            convertedChar = "Ӏу";
                        } else if (withPrevChar.equals("ie")) {
                            convertedChar = "Ӏэ";
                        } else if (withPrevChar.equals("ı'")) { // TODO doesn't seem to work??
                            convertedChar = "Ӏ";
                        } else if (withPrevChar.equals("eo")) {
                            convertedChar = "Ӏо";
//                        } else if (withPrevChar.equals("ey")) { // TODO Wewey : Оой vs. dey : дэи
//                            convertedChar = "и";
//                        } else if (withPrevChar.equals("ay")) {
//                            convertedChar = "и";
//                        } else if (withPrevChar.equals("ay")) {
////                        } else if (currentChar.equals("y")) {
//                            if ((i + 1) == lenLatin) {
//                                convertedChar = "и";
//                            }
//                        } else if (withPrevChar.equals("аи")) {
//                            convertedChar = "yı";
//                            if ((i + 1) == lenLatin) {
//                                convertedChar = "y";
//                            }
                        }
                    }
                    i = i + 1;
                    if (isUpper) {
                        convertedChar = capitalize(convertedChar, false, true);
                    }
                    newStr = newStr + convertedChar;
                    break;
                }
            }
            if (i >= lenLatin) {
                break;
            }
            if (matched) {
                continue;
            }
            if (inspect) {
                Log.d(TAG, "YYY8c5 not matched: " + currentChar);
            }
            // no match
            newStr = newStr + currentCharOrig;
            i = i + currentCharOrig.length();
        }
        // TODO is next one needed?
        newStr.replaceAll("ı", "Ӏ");
        return newStr;
    }

    private boolean originalIsUpperCase(String original, boolean isLatin, boolean isCyrillic) {
        boolean isUpper = true;
        final String firstChar = original.substring(0, 1);
        if (firstChar.equals(toLowerCase(firstChar, isLatin, isCyrillic)) || firstChar.equals("I")) {
            isUpper = false;
        }
        return isUpper;
    }

    private static final AdigaCharacterUtils mInstance = new AdigaCharacterUtils();
    private final ArrayMap<String, String> mSpecial2EnglishCharacterMap;
    private final ArrayMap<String, String> mCyrillicExtra2BasicCharacterMap;
    private final String mCyrillicAllowedChars = "АБВГДЕЖЗИЙКЛМНОӀПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя?,.;:-()[]{}_\"'`";

    private final ArrayMap<String, String> mCyrillic2LatinSingleCharMap;
    private final ArrayMap<String, String> mCyrillic2LatinRegexp2Chars;
    private final ArrayMap<String, String> mCyrillic2LatinRegexp3Chars;
    private final ArrayMap<String, String> mCyrillic2LatinRegexp4Chars;

    private final ArrayMap<String, String> mCyrillic2LatinRegexpPrefixes1Char;
    private final ArrayMap<String, String> mCyrillic2LatinRegexpPrefixes2Chars;
    private final ArrayMap<String, String> mCyrillic2LatinRegexpPrefixes3Chars;

    private final ArrayMap<String, String> mCyrillic2LatinRegexpPostfixes;

    private final ArrayMap<String, String> mLatin2CyrillicSingleCharMap;
    private final ArrayMap<String, String> mLatin2CyrillicRegexp2Chars;
    private final ArrayMap<String, String> mLatin2CyrillicRegexp3Chars;
    private final ArrayMap<String, String> mLatin2CyrillicRegexp4Chars;

    private final ArrayMap<String, String> mLatin2CyrillicRegexpPrefixes1Char;
    private final ArrayMap<String, String> mLatin2CyrillicRegexpPrefixes2Chars;
    private final ArrayMap<String, String> mLatin2CyrillicRegexpPrefixes3Chars;
    private final ArrayMap<String, String> mLatin2CyrillicRegexpPostfixes;

    private static final String TAG = AdigaCharacterUtils.class.getSimpleName();
}
/*
1 1 2 29 Uis -> Wis
1 1 2 36 Sié -> Siye
1 1 3 45 U -> Wı
1 1 3 46 Uas -> Was   Wııse -> Wıse
1 1 3 48 Uaya -> Waya
1 1 3 49 Uase -> Wase   Uéme -> Wéme   Wııse -> Wıse
1 1 3 50

Yce vs. Yыce
O vs. Yэ vs. Oэ - 1 1 4 69
Oyи vs. Yэyи - 1 1 4 71
Oc vs. Yэc - 1 1 4 70
Okyo vs. Yэkyo - 1 1 5 83
Atэ vs. Iate - 1 2 2 24
K appears twice - 1 2 4 62
Hыcэ appears twice - 1 3 5 79
Бaй vs. Бaи - 1 5 4 68

######### second part starting from stage 1 lesson 6:
1 6 2 30: Жын and Жъын converted the same: Jın
1 6 2 33: same for Jemaf

1 9 5 64: Baze appears twice

# last bunch
2 4 71: removed 'a': desaep ->desep
áçćéǵğḣıḱĺöṕşśšṫüź
ÁÇĆÉǴĞḢİḰĹÖṔŞŚŠṪÜŹ

А Б В Г Д Е Ё Ж З И Й К Л М Н О Ӏ П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я
а б в г д е ё ж з и й к л м н о   п р с т у ф х ц ч ш щ ъ ы ь э ю я

*/
