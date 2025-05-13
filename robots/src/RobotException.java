public class RobotException extends Exception{
    public RobotException(String message) {
        super("Robot error : "+message);
    }
}