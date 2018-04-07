# -*- coding: utf-8 -*-
"""
Created on Thu Apr 20 00:18:41 2017

@author: krist
"""
import pymorphy2
import string
import argparse
from collections import defaultdict

def parse_doc(input_f, stopwords_f, freq = True):
    morph = pymorphy2.MorphAnalyzer()
    
    stopwords = [word for word in stopwords_f.read().lower().split()]
    stopwords_f.close()
    
    trantab = str.maketrans("", "", string.punctuation)
    texts = [] #list of lists with tokens
    #processing documents: filename -> list of tokens (freq >1)
    
    tokens = [morph.parse(word.translate(trantab))[0].normal_form for word in input_f.read().lower().split() if word not in stopwords]
    input_f.close()
    if freq:
        frequency = defaultdict(int)
        for token in tokens:
            frequency[token] += 1
        texts =[word for word in tokens if frequency[word]>1]
    else:
        texts =[word for word in tokens]
    return texts

def save_tokens(texts, file):
    for token in texts:
        file.write(token+' ')
    file.close()
        
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Processing the document.')
    parser.add_argument('input_file', type=open,
                       help='file to be processed')
    parser.add_argument('stopwords_file', type=open,
                       help='file with stopwords')
    parser.add_argument('output_file', type=argparse.FileType('w'),
                       help='destination file name')
    args = parser.parse_args()
    
    save_tokens(parse_doc(args.input_file, args.stopwords_file), args.output_file)
    