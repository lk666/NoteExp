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
//     * ���������������Ͻ��в�����ĳЩʵ�ù��߷�����ʵ�֡�
//     */
//    private Types typeUtilks;
//    /**
//     * ����������Ԫ���Ͻ��в�����ĳЩʵ�ù��߷�����ʵ�֡�
//     */
//    private Elements elementUtils;
//    /**
//     * ��������������Դ��������ļ��� Filer��
//     */
//    private Filer filer;
//    /**
//     * ��������������󡢾���������֪ͨ�� Messager��
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
//    // ������Ҫ�����ע���࣬ȫ��
//    // ������Խ����ص����ͷ�Χ��С�����ܷŴ�
//    @Override
//    public ImmutableSet<String> getSupportedAnnotationTypes() {
//        return ImmutableSet.of(FakeBind.class.getCanonicalName());
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        // ����ʹ�� @AutoParcelable ע��� Element ���ϡ�
//        // Element ����Ϊ�ࣨTypeElement����������ExecutableElement��������(VariableElement)�ȡ�
//        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith
//                (FakeBind.class);
//        // ���˳������͵�Ԫ��
//        // �ӿ�Ҳ��һ��TypeElement���ͣ���Ҫ���˵�
//        List<VariableElement> types =
//                new ImmutableList.Builder<VariableElement>()
//                        .addAll(ElementFilter.fieldsIn(annotatedElements))
//                        .build();
//
//        processElements(elementUtils, filer, types);
//        // ���� true ����������������Ҫ����AutoParcelע��
//        return false;
//    }
//
//    private static void processElements(Elements elementUtils, Filer filer, List<VariableElement>
//            variableElements) {
//        MethodSpec.Builder bindBuilder = null;
//        // ���ط�װ��Ԫ�أ����ϸ������ϣ��������Ԫ�أ���xxxActivity��TypeElement
//        TypeElement classElement = null;
//        // ������
//        PackageElement packageElement = null;
//        // ȫ����
//        String fullClassName = null;
//        // ����
//        String className = null;
//        // ����
//        String pckName = null;
//
//        for (VariableElement variableElement : variableElements) {
//            //            if (field.getKind() != ElementKind.CLASS) {
//            //                // ��ʾ���������������ʾ�Ĵ��󣬱���˴��ڷ�����ʹ����AutoParcelableע��
//            //                messager.printMessage(Diagnostic.Kind.ERROR,
//            // "�ڷ�����ʹ����AutoParcelableע��");
//            //                return true;
//            //            }
//
//
//            // �ֶ���
//            String vName = variableElement.getSimpleName().toString();
//            // ����
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
//                        // Elementת����TypeName
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
