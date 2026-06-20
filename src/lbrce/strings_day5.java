package lbrce;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class strings_day5 {
    //Question 1: reverse a string
    public static String reverseStr(String s) {
        char[] arr = s.toCharArray();
        //Two Pointer
        int st = 0, end = arr.length - 1;

        while(st < end) {
            char temp = arr[st];
            arr[st] = arr[end];
            arr[end] = temp;
            st++;
            end--;
        }
        return new String(arr);

        // return new StringBuilder(s).reverse().toString();
    }

    //Question 2: Valid Palindrome
    public static boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while(left < right) {
            char charLeft = s.charAt(left);
            char charRight = s.charAt(right);

            if(!Character.isLetterOrDigit(charLeft))
                left++;
            else if(!Character.isLetterOrDigit(charRight))
                right--;
            else {
                if(Character.toLowerCase(charLeft) != Character.toLowerCase(charRight))
                    return false;
                left++;
                right--;
            }
        }
        return true;

        //Race car -> racecar
        // String cleaned = s.replaceAll(
        //     "[^a-zA-Z0-9]", "")
        //     .toLowerCase();
        // String reversed = new StringBuilder(s)
        //                         .reverse().toString();
        // return cleaned.equals(reversed);
    }

    //Q3: Count Characters
    public static void countCharacters(String s) {
        int vowels = 0, consonants = 0, digits = 0, spaces = 0;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if(Character.isDigit(ch))
                digits++;
            else if(Character.isWhitespace(ch))
                spaces++;
            else if(Character.isLetter(ch)) {
                ch = Character.toLowerCase(ch);
                if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        System.out.printf(
            "V: %d, C: %d, D: %d, S: %d",
            vowels, consonants, digits, spaces);
        
        //strems
        // long vowels = s.chars().filter(
        //     c -> "aeiouAEIOU".indexOf(c) != -1)
        //     .count();
        // long consonants = s.chars().filter(
        //     c -> Character.isLetter(c) &&
        //     "aeiouAEIOU".indexOf(c) == -1)
        //     .count();
        // long digits = s.chars().filter(
        //     Character::isDigit).count();
        // long spaces = s.chars().filter(
        //     Character::isWhitespace).count();
        
    }

    //Question 4: Non repeating Character
    public static char firstNonRepeating(String s) {
        Map<Character, Integer> count = new LinkedHashMap<>();

        for(char c: s.toCharArray()) {
            count.put(c, count.getOrDefault(
                c, 0) + 1);
        }
        for(Map.Entry<Character, Integer> entry : count.entrySet()) {
            if(entry.getValue() == 1)
                return entry.getKey();
        }
        return '_';
    }

    //Question 5: Anagrams
    public static boolean isAnagram(String s1, String s2) {
        if(s1.length() != s2.length()) return false;

        Map<Character, Integer> count = new HashMap<>();
        for(int i = 0; i < s1.length(); i++) {
            count.put(s1.charAt(i), count.getOrDefault(s1.charAt(i), 0) + 1);
            count.put(s2.charAt(i), count.getOrDefault(s2.charAt(i), 0) - 1);
        }
        System.out.println(count);

        for(int c : count.values())
            if(c != 0) return false;

        return true;
    }

    //Question 6: KPM Algorithm
    public static int kpmAlgo(String haystack, String needle) {
        int m = needle.length();
        int[] lps = new int[m];

        int len = 0, i = 1;
        while( i < m) {
            if(needle.charAt(i) == needle.charAt(len)) {
                lps[i++] = ++len;
            } else if(len != 0) {
                len = lps[len - 1]; //Fallback
            } else {
                lps[i++] = 0;
            }
        }

        //search phase
        int j = 0; i = 0;
        while(i < haystack.length()) {
            if(needle.charAt(j) == haystack.charAt(i)) {
                j++; i++;
            }
            if(j == m) return i - j; //Match found
            else if(i < haystack.length() &&
                needle.charAt(j) != haystack.charAt(i)) {
                    if(j != 0) j = lps[j-1];
                    else i++;
            }
        }
        return -1;
    }

    //Question 7: Longest Substring without repeating
    public static int longestSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int left = 0, right = 0, maxLength = 0;

        while(right < s.length()) {
            if(!window.contains(s.charAt(right))) {
                window.add(s.charAt(right));
                maxLength = Math.max(maxLength, right-left+1);
                right++;
            }
            else {
                window.remove(s.charAt(left));
                left++;
            }
        }
        return maxLength;
    }

    //Question 8: Run-Length Encoding
    public static String runLengthEncoding(String s) {
        if(s == null || s.isEmpty()) return "";

        StringBuilder encoded = new StringBuilder();
        int count = 1;

        for(int i = 1; i <= s.length(); i++) {
            if(i == s.length() ||
                s.charAt(i) != s.charAt(i - 1)) {
                encoded.append(count)
                    .append(s.charAt(i-1));
                count = 1;
            }
            else {
                count++;
            }
        }
        return encoded.toString();

        // StringBuilder encoded = new StringBuilder();
        // Matcher m = Pattern.compile("(.)\\1*")
        //                             .matcher(s);
        // while(m.find())
        //     encoded.append(
        //         m.group()
        //         .length()).append(m.group(1)
        //     );
        
        // return encoded.toString();
    }

    //Question 9: longest palindrome subString
    public static String longestPalindromeSubstring(
        String s) {
        if(s == null || s.length() < 2) return s;

        int start = 0, end = 0;

        for(int i = 0; i < s.length(); i++) {
            //checking for odd length
            int len1 = expand(s, i, i);
            //check for even length
            int len2 = expand(s, i, i+1);
            //check max
            int len = Math.max(len1, len2);

            if(len > end - start) {
                start = i - (len-1)/2;
                end = i + len/2;
            }
        }
        return s.substring(start, end + 1);
    }
    private static int expand(String s, int left, int right) {
        while(left >= 0 && right < s.length()
            && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
        }
        return right - left - 1;
    }

    public static void main(String[] args) {
        //Q1
        // System.out.println(reverseStr("hello"));

        //Q2
        // System.out.println(validPalindrome("Race car"));

        //Q3
        // countCharacters("2026 Friday 19");

        //Q4
        // System.out.println(firstNonRepeating("swiss"));

        //Q5
        // System.out.println(isAnagram("catsab", "acting"));

        //Q6
        // System.out.println(kpmAlgo("ABABDABABCD", "ABABC"));

        // Q7
        // System.out.println(longestSubstring("aaebcdefabaa"));

        //Q8
        // System.out.println(runLengthEncoding("a"));

        //Q9
        System.out.println(longestPalindromeSubstring("babcdedcadc"));
    }
}
