package functionInterface.demo02Lambda;

@FunctionalInterface
public interface MessageBuilder {
    // 定义一个拼接消息的抽象方法
    String builderMessage();
}
