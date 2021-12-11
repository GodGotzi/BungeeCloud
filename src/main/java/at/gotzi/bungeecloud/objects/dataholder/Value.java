package at.gotzi.bungeecloud.objects.dataholder;

public record Value<T>(T obj) {

    public T getObj() {
        return obj;
    }
}
