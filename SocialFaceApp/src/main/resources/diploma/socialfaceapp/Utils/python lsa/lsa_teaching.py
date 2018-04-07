# -*- coding: utf-8 -*-
"""
Created on Wed Apr 19 00:28:09 2017

@author: krist
"""

from gensim import corpora, models, similarities
import gensim
import process_file
import os, inspect

class MyCorpus(object):
    def __init__(self, files, dictionary):
        self.files = files
        self.dict = dictionary
    def __iter__(self):
        for doc in self.files:
            with open(doc) as file:
                yield self.dict.doc2bow(file.read().lower().split())

class LSA():
    
    def __init__(self, main_dir, docs): #имена файлов
        self.dir = main_dir
        self.file_dir = main_dir+'\\themes\\'
        self.stopwords = main_dir+'\\stopwords.txt'
        # инициализируем сами документы
        self.documents = docs
        self.dictionary_dir = main_dir+'\\tmp\\socialface.dict'
        self.corpus_dir = main_dir+'\\tmp\\socialface.mm'
        self.lsi_dir = main_dir+'\\tmp\\socialface.lsi'
        self.sim_dir = main_dir+'\\tmp\\socialface.index'
    #    
    def process_docs(self, freq=True):
        self.texts = []
        for doc in self.documents:
            self.texts.append(process_file.parse_doc(open(self.file_dir+doc), open(self.stopwords), freq))
            
    def prepare_corpus_from_texts(self):
        self.dictionary = corpora.Dictionary(self.texts)
        self.dictionary.save_as_text (self.dictionary_dir)  # store the dictionary, for later use
        
        self.corpus = [self.dictionary.doc2bow(text) for text in self.texts]
        corpora.MmCorpus.serialize(self.corpus_dir, self.corpus) # store to disk, for later use
        
        #print(self.dictionary, self.corpus)
        
    def prepare_corpus_from_files(self):
        self.dictionary = corpora.Dictionary.load_from_text(self.dictionary_dir)
        self.corpus = corpora.MmCorpus(self.corpus_dir)
        
        #print(self.dictionary, self.corpus)
        
    def process_lsi(self):
        self.lsi = models.lsimodel.LsiModel(corpus=self.corpus, id2word=self.dictionary)
        self.lsi.save(self.lsi_dir)
        
    def load_lsi(self):
        self.lsi = gensim.models.lsimodel.LsiModel.load(self.lsi_dir)
        
    def process_similarity(self):
        
        index = similarities.MatrixSimilarity(self.lsi[self.corpus])
        index.save(self.sim_dir)
        
    def doc_similarities(self, doc):
        doc_text = process_file.parse_doc(doc, open(self.stopwords), False)
        vec_bow = self.dictionary.doc2bow(doc_text)
        vec_lsi = self.lsi[vec_bow]
        index = similarities.MatrixSimilarity.load(self.sim_dir)
        sims = index[vec_lsi]
        sims = sorted(enumerate(sims), key=lambda item: -item[1])
        print(sims)


main_dir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))

docs_dir = main_dir + '\\themes'

docs = []


for (dirpath, dirnames, filenames) in os.walk(docs_dir):
    docs.extend(filenames)
    break 

lsaManager = LSA(main_dir, docs)
#lsaManager.process_docs()
lsaManager.prepare_corpus_from_files()
#lsaManager.process_lsi()
lsaManager.load_lsi()        
#lsaManager.process_similarity()
