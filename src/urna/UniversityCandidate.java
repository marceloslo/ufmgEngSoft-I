package urna;
// #if Universidade
//@
//@public class UniversityCandidate extends Candidate {
//@	
//@	public String nationality;
//@
//@    public UniversityCandidate(Builder builder) {
//@        super(builder);
//@        this.nationality = builder.nationality;
//@    }
//@
//@    public static class Builder extends Candidate.Builder<Builder> {
//@        private String nationality;
//@
//@        public Builder nationality(String nationality) {
//@            verifyValidNationality(nationality);
//@            this.nationality = nationality;
//@            return this;
//@        }
//@        
//@        @Override
//@        public Builder getThis() {
//@            return this;
//@        }
//@        
//@        public UniversityCandidate build() {
//@            return new UniversityCandidate(this);
//@        }
//@        
//@        protected void verifyValidNationality(String nationality){
//@            
//@            if (nationality == null)
//@                throw new IllegalArgumentException("nationality mustn't be null");
//@    
//@            if (nationality.isEmpty())
//@                throw new IllegalArgumentException("nationality mustn't be empty");
//@        }
//@
//@    }
//@
//@}
//@
// #endif
