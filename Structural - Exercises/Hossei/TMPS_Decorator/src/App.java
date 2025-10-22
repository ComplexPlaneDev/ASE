public class App {
    public static void main(String[] args) throws Exception {

        Message original = new BasicTextMessage("Original Message: Hello World");

        Message encrypted = new EncryptionDecorator(original);
        System.out.println("After encryption: " + encrypted.process());
        System.out.println("Size: " + encrypted.getSize());

        Message compressedEncrypted = new CompressionDecorator(encrypted);
        System.out.println("After compression: "+ compressedEncrypted.process());
        System.out.println("Size: " + compressedEncrypted.getSize());

        Message stampedCompressedEncrypted = new TimestampDecorator(compressedEncrypted);
        System.out.println("After timestamp: "+ stampedCompressedEncrypted.process());
        System.out.println("Size: " + stampedCompressedEncrypted.getSize());
    }
}
