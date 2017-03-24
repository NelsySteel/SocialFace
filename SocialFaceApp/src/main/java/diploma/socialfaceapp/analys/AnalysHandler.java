package diploma.socialfaceapp.analys;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tml.corpus.CorpusParameters.DimensionalityReduction;
import tml.corpus.CorpusParameters.TermSelection;
import tml.corpus.SearchResultsCorpus;
import tml.storage.Repository;
import tml.vectorspace.NoDocumentsInCorpusException;
import tml.vectorspace.NotEnoughTermsInCorpusException;
import tml.vectorspace.TermWeighting.GlobalWeight;
import tml.vectorspace.TermWeighting.LocalWeight;
import tml.vectorspace.TermWeightingException;
import tml.vectorspace.operations.PassagesSimilarity;

public class AnalysHandler {
    private List results;
    private List input;
    
    public AnalysHandler(){
        //not yet implemented
    }
    public static class LSA{        
        public static void AddingFilesToRepository(String folder) throws IOException, SQLException{
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            Repository repository = new Repository(s+"/lib/tml-java/uploaded");

            repository.addDocumentsInFolder(folder);

            System.out.println("Documents added to repository successfully!");
        }
        
        public static String execute(){
            Repository repository = null;
            try {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                repository = new Repository(s+"/lib/tml-java/uploaded");
            } catch (IOException | SQLException ex) {
                Logger.getLogger(AnalysHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            SearchResultsCorpus corpus = new SearchResultsCorpus("type:document");
            corpus.getParameters().setTermSelectionCriterion(TermSelection.DF);
            corpus.getParameters().setTermSelectionThreshold(0);
            corpus.getParameters().setDimensionalityReduction(DimensionalityReduction.NUM);
            corpus.getParameters().setDimensionalityReductionThreshold(1);
            corpus.getParameters().setTermWeightGlobal(GlobalWeight.Entropy);
            corpus.getParameters().setTermWeightLocal(LocalWeight.LOGTF);
            
            try {
                corpus.load(repository);
            } catch (NotEnoughTermsInCorpusException | IOException | NoDocumentsInCorpusException | TermWeightingException ex) {
                Logger.getLogger(AnalysHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintStream terms = null;
            try {
                terms = new PrintStream("terms.txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AnalysHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String term :corpus.getTerms()){
                terms.println(term);
                
            }
            System.out.println("Corpus loaded and Semantic space calculated");
            System.out.println("Total documents:" + corpus.getPassages().length);

            PassagesSimilarity distances = new PassagesSimilarity();
            distances.setCorpus(corpus);
            try {
                distances.start();
            } catch (Exception ex) {
                Logger.getLogger(AnalysHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return distances.getResultsXML();
        }
    }
}
