package github.com.ASE;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CopyOnWrite {
	@SuppressWarnings("unchecked")
    public static <T> T wrap(T instance, Class<T> iface) {
        return (T) Proxy.newProxyInstance(
            iface.getClassLoader(),
            new Class<?>[]{iface},
            new COWHandler<>(instance)
        );
    }

    private static class COWHandler<T> implements InvocationHandler {
        private T target;

        COWHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();
            
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                T copy = shallowCopy(target);
                method.invoke(copy, args);
                target = copy;
                return proxy;
            }

            return method.invoke(target, args);
        }

        @SuppressWarnings("unchecked")
		private T shallowCopy(T obj) throws Exception {
            Class<?> clazz = obj.getClass();
			T copy = (T) clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(copy, field.get(obj));
            }
            return copy;
        }
    }
}
