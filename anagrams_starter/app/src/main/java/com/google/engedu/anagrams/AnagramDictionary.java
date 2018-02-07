/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    HashMap<String, ArrayList<String>> lettersToWord;



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordList =  new ArrayList<String>();
        wordSet = new HashSet<String>();
        lettersToWord = new HashMap<String, ArrayList<String>>();

        while((line = in.readLine()) != null) {
            String word = line.trim();
            String sortedWord  = sortLetters(word);

            //add word to hashset
            wordSet.add(word);


            //add word to the hashmap
            if(!lettersToWord.containsKey(sortedWord)) {
                ArrayList<String> init = new ArrayList<String>();
                init.add(word);
                lettersToWord.put(sortedWord, init);
            }
            lettersToWord.get(sortedWord).add(word);

            wordList.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        boolean flag = false;

        List<String> words = getAnagramsWithOneMoreLetter(base);
        for(String w: words){
            if(word.equals(w)) flag = true;
        }

        return wordSet.contains(word) && flag;
    }



    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for(String word: wordList){
            if(word.length() == targetWord.length() && sortLetters(word).equals(sortLetters(targetWord))){
                result.add(targetWord);
            }
        }
        return result;
    }

    public String sortLetters(String word){
        char[] c =word.toCharArray();
        Arrays.sort(c);
        String sorted = c.toString();
        return sorted;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char alphabet = 'a'; alphabet<'z'; alphabet++){
            for(int j = 0; j< word.length() ; j++){
                String newWord;
                if(j == 0) newWord = alphabet + word.substring(1,word.length()-1);
                else if(j == word.length()-1) newWord = word.substring(0,word.length()-1) + alphabet;
                else newWord = word.substring(0,j) + alphabet + word.substring(j,word.length()-1);
                result.add(newWord);
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        return "post";
    }
}
