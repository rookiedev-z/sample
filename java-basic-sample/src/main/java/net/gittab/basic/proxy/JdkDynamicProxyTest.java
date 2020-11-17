package net.gittab.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * JdkDynamicProxy.
 *
 * @author rookiedev 2020/11/14 10:46
 **/
public class JdkDynamicProxyTest {

    interface IService{

        void process();
    }

    static class BusinessServiceImpl implements IService{

        @Override
        public void process() {
            System.out.println("process business logic");
        }

        public void process1() {
            System.out.println("process business logic");
        }
    }

    static class JdkProxyObject implements InvocationHandler {

        private Object target;

        public Object bind(Object object){
            this.target = object;
            return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("process business logic start");
            Object result = method.invoke(this.target, args);
            System.out.println("process business logic end");
            return result;
        }
    }

    static class CglibProxyObject0 implements MethodInterceptor{

        @Override
        public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("0 process business logic start");
            // note, call invokeSuper method
            Object result = methodProxy.invokeSuper(object, objects);
            System.out.println("0 process business logic end");
            return result;
        }
    }

    static class CglibProxyObject1 implements MethodInterceptor{

        @Override
        public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("1 process business logic start");
            // note, call invokeSuper method
            Object result = methodProxy.invokeSuper(object, objects);
            System.out.println("1 process business logic end");
            return result;
        }
    }

    static class ProxyFilter implements CallbackFilter{
        @Override
        public int accept(Method method) {
            if(method.getName().equals("process")){
                return 0;
            }
            return 1;
        }
    }



    public static void main(String[] args) {
        // jdk dynamic proxy
        IService service = (IService) new JdkProxyObject().bind(new BusinessServiceImpl());
        service.process();

        // cglib dynamic proxy
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BusinessServiceImpl.class);
        // enhancer.setCallback(new CglibProxyObject0());
        enhancer.setCallbacks(new Callback[]{new CglibProxyObject0(), new CglibProxyObject1(), NoOp.INSTANCE});
        enhancer.setCallbackFilter(new ProxyFilter());
        BusinessServiceImpl service1 = (BusinessServiceImpl) enhancer.create();
        service1.process();
        service1.process1();
    }


}
