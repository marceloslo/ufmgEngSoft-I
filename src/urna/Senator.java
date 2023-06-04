package urna;

//#if Federal || Municipal

public class Senator extends FederalLegislativeCandidate{
      
    public Senator(Builder builder) {
        super(builder);
    }

    public static class Builder extends FederalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public Senator build() {
            return new Senator(this);
        }

    }    

};
//#endif
