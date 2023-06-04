public class DepartmentHead extends UniversityCandidate{
      
    public DepartmentHead(Builder builder) {
        super(builder);
    }

    public static class Builder extends UniversityCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public DepartmentHead build() {
            return new DepartmentHead(this);
        }

    }    

};