//package lk.cn.com.lib_my_compiler;
//
//import com.google.auto.service.AutoService;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableSet;
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.MethodSpec;
//import com.squareup.javapoet.TypeName;
//import com.squareup.javapoet.TypeSpec;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.Messager;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.Processor;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.Modifier;
//import javax.lang.model.element.PackageElement;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.element.VariableElement;
//import javax.lang.model.type.TypeMirror;
//import javax.lang.model.util.ElementFilter;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
//import lk.cn.com.lib_my_annotation.FakeBind;
//
//@AutoService(Processor.class)
//public class FakeButterKnifeProcessor extends AbstractProcessor {
//    /**
//     * 返回用来在类型上进行操作的某些实用工具方法的实现。
//     */
//    private Types typeUtilks;
//    /**
//     * 返回用来在元素上进行操作的某些实用工具方法的实现。
//     */
//    private Elements elementUtils;
//    /**
//     * 返回用来创建新源、类或辅助文件的 Filer。
//     */
//    private Filer filer;
//    /**
//     * 返回用来报告错误、警报和其他通知的 Messager。
//     */
//    private Messager messager;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnvironment) {
//        super.init(processingEnvironment);
//        typeUtilks = processingEnvironment.getTypeUtils();
//        elementUtils = processingEnvironment.getElementUtils();
//        filer = processingEnvironment.getFiler();
//        messager = processingEnvironment.getMessager();
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    // 返回需要处理的注解类，全名
//    // 子类可以将返回的类型范围缩小，不能放大
//    @Override
//    public ImmutableSet<String> getSupportedAnnotationTypes() {
//        return ImmutableSet.of(FakeBind.class.getCanonicalName());
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        // 返回使用 @AutoParcelable 注解的 Element 集合。
//        // Element 可以为类（TypeElement），方法（ExecutableElement），变量(VariableElement)等。
//        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith
//                (FakeBind.class);
//        // 过滤出类类型的元素
//        // 接口也是一种TypeElement类型，需要过滤掉
//        List<VariableElement> types =
//                new ImmutableList.Builder<VariableElement>()
//                        .addAll(ElementFilter.fieldsIn(annotatedElements))
//                        .build();
//
//        processElements(elementUtils, filer, types);
//        // 返回 true ，其他处理器不需要处理AutoParcel注解
//        return false;
//    }
//
//    private static void processElements(Elements elementUtils, Filer filer, List<VariableElement>
//            variableElements) {
//        MethodSpec.Builder bindBuilder = null;
//        // 返回封装此元素（非严格意义上）的最里层元素，如xxxActivity的TypeElement
//        TypeElement classElement = null;
//        // 所属包
//        PackageElement packageElement = null;
//        // 全类名
//        String fullClassName = null;
//        // 类名
//        String className = null;
//        // 包名
//        String pckName = null;
//
//        for (VariableElement variableElement : variableElements) {
//            //            if (field.getKind() != ElementKind.CLASS) {
//            //                // 演示向第三方开发者提示的错误，比如此处在非类上使用了AutoParcelable注解
//            //                messager.printMessage(Diagnostic.Kind.ERROR,
//            // "在非类上使用了AutoParcelable注解");
//            //                return true;
//            //            }
//
//
//            // 字段名
//            String vName = variableElement.getSimpleName().toString();
//            // 类型
//            TypeMirror typeMirror = variableElement.asType();
//            String type = typeMirror.toString();
//
//            if (bindBuilder == null) {
//                classElement = (TypeElement) variableElement.getEnclosingElement();
//                packageElement = elementUtils.getPackageOf(classElement);
//                fullClassName = classElement.getQualifiedName().toString();
//                className = classElement.getSimpleName().toString();
//                pckName = packageElement.getQualifiedName().toString();
//                bindBuilder = MethodSpec.methodBuilder("bind")
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .returns(TypeName.VOID)
//                        // Element转换成TypeName
//                        .addParameter(TypeName.get(typeMirror), "aty");
//            }
//
//            FakeBind ano = variableElement.getAnnotation(FakeBind.class);
//            bindBuilder.addStatement("aty.$S = ($S)aty.findViewById($S);", vName, type,
//                    ano.value());
//        }
//
//        if (bindBuilder == null) {
//            return;
//        }
//        MethodSpec bind = bindBuilder.build();
//        TypeSpec classT = TypeSpec.classBuilder(className)
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(bind)
//                .build();
//
//        JavaFile file = JavaFile.builder(pckName, classT)
//                .build();
//
//        try {
//            file.writeTo(filer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
