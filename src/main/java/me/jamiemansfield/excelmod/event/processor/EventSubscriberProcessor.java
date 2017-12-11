/*
 * This file is part of ExcelMod, licensed under the MIT License (MIT).
 *
 * Copyright (c) Jamie Mansfield <https://www.jamierocks.uk/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.jamiemansfield.excelmod.event.processor;

import me.jamiemansfield.excel.util.ap.AnnotationProcessor;
import me.jamiemansfield.excelmod.event.Subscribe;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * An annotation processor to verify that the usage of @{@link Subscribe} is correct.
 */
@SupportedAnnotationTypes("me.jamiemansfield.excelmod.event.Subscribe")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class EventSubscriberProcessor extends AnnotationProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!this.contains(annotations, Subscribe.class)) {
            return false;
        }

        for (final Element element : roundEnv.getElementsAnnotatedWith(Subscribe.class)) {
            if (element.getKind() != ElementKind.METHOD) {
                this.error("Only methods can be annotated with @Subscribe", element);
                continue;
            }

            final ExecutableElement methodElement = (ExecutableElement) element;

            // Check the enclosing class
            ////
            if (methodElement.getEnclosingElement().getKind().isInterface()) {
                this.error("Event subscribers may not be defined in interfaces!", element);
            }

            // Check the subscriber method
            ////

            // - Check modifiers
            ////
            if (!methodElement.getModifiers().contains(Modifier.PUBLIC)) {
                this.error("Event subscribers must be public!", element);
            }
            if (methodElement.getModifiers().contains(Modifier.STATIC)) {
                this.error("Event subscribers may not be static!", element);
            }
            if (methodElement.getModifiers().contains(Modifier.ABSTRACT)) {
                this.error("Event subscribers may not be abstract!", element);
            }

            // - Check return type
            ////
            if (methodElement.getReturnType().getKind() != TypeKind.VOID) {
                this.error("Event subscribers musts return void!", element);
            }

            // - Check parameters
            ////
            final List<? extends VariableElement> parameters = methodElement.getParameters();
            if (parameters.isEmpty() ||
                    this.isTypeSubclass(parameters.get(0), "uk.jamierocks.flint.api.event.Event") ||
                    parameters.size() != 1) {
                this.error("The first (and only) parameter must be an Event!", element);
            }
        }

        return false;
    }

    private boolean isTypeSubclass(final Element typedElement, final String subclass) {
        final Elements elements = this.processingEnv.getElementUtils();
        final Types types = this.processingEnv.getTypeUtils();

        final TypeMirror event = types.getDeclaredType(elements.getTypeElement(subclass));
        return types.isAssignable(typedElement.asType(), event);
    }

}