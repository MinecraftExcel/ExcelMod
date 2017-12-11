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

package me.jamiemansfield.excelmod.event;

/**
 * The ExcelMod event bus.
 */
public class EventBus {

    /**
     * Registers the given event subscriber to the given mod.
     *
     * @param mod The mod instance
     * @param subscriber The subscriber to register
     */
    public void register(final Object mod, final Object subscriber) {
        // TODO: implement
    }

    /**
     * Registers the given {@link EventSubscriber} to the given mod.
     *
     * @param mod The mod instance
     * @param subscriber The subscriber to register
     */
    public void register(final Object mod, final EventSubscriber subscriber) {
        // TODO: implement
    }

    /**
     * Fires the given event.
     *
     * @param event The event
     * @param <E> The type of event
     * @return The event
     */
    public <E extends Event> E fire(final E event) {
        // TODO: implement
        return event;
    }

}
