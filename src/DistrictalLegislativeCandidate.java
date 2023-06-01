package urna;

public class DistrictalLegislativeCandidate extends Candidate {

    public String district;

    protected DistrictalLegislativeCandidate(Builder builder) {
        super(builder);
        this.district = builder.district;
    }

    public static abstract class Builder<T extends Builder<T>> 
                            extends Candidate.Builder<Builder<T>> {

        private String district;

        public T district(String district) {
            verifyValidDistrict(district);this.district = district;
            return this.getThis();
        }

        public DistrictalLegislativeCandidate build() {
            return new DistrictalLegislativeCandidate(this);
        }

        protected void verifyValidDistrict(String district){
            
            if (district == null)
                throw new IllegalArgumentException("district mustn't be null");
    
            if (district.isEmpty())
                throw new IllegalArgumentException("district mustn't be empty");
        }
        
        @Override
        public abstract T getThis();

    }

}