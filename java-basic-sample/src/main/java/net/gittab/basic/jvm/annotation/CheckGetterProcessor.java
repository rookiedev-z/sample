package net.gittab.basic.jvm.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

/**
 * CheckGetterProcessor.
 *
 * @author rookiedev 2020/11/10 10:52
 **/
@SupportedAnnotationTypes("net.gittab.basic.jvm.annotation.CheckGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class CheckGetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CheckGetter.class);

        for (TypeElement annotatedClass : ElementFilter.typesIn(elements)) {
            for (VariableElement field : ElementFilter.fieldsIn(annotatedClass.getEnclosedElements())) {
                if (!containsGetter(annotatedClass, field.getSimpleName().toString())) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("getter not found for '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName()));
                }
                System.out.println("test");
            }
        }
        return true;
    }

    private static boolean containsGetter(TypeElement typeElement, String name) {
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
            if (!executableElement.getModifiers().contains(Modifier.STATIC)
                    && executableElement.getSimpleName().toString().equals(getter)
                    && executableElement.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
