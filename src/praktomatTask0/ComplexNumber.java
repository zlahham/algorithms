package praktomatTask0;

public class ComplexNumber {

  private double realPart;
  private double imaginaryPart;

  public ComplexNumber(double realPart, double imaginaryPart) {
    this.realPart      = realPart;
    this.imaginaryPart = imaginaryPart;
  }

  public double realPart() {
    return this.realPart;
  }

  public double imaginaryPart() {
    return this.imaginaryPart;
  }

  public ComplexNumber add(ComplexNumber c) {
    double newReal = this.realPart + c.realPart;
    double newImaginary = this.imaginaryPart + c.imaginaryPart;
    return new ComplexNumber(newReal, newImaginary);
  }

  public ComplexNumber multiply(ComplexNumber c) {
    double newReal = (this.realPart * c.realPart) - (this.imaginaryPart + c.imaginaryPart);
    double newImaginary = (this.realPart * c.imaginaryPart) + (this.imaginaryPart * c.realPart);
    return new ComplexNumber(newReal, newImaginary);
  }

}
