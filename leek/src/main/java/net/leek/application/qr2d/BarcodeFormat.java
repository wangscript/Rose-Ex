/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.leek.application.qr2d;

import java.util.Hashtable;

/**
 * Enumerates barcode formats known to this package. Please keep alphabetized.
 * 
 * @author Sean Owen
 */
public final class BarcodeFormat {

	private static final Hashtable<String, BarcodeFormat> VALUES = new Hashtable<String, BarcodeFormat>();

	/** QR Code 2D barcode format. */
	public static final BarcodeFormat QR_CODE = new BarcodeFormat("QR_CODE");

	private final String name;

	private BarcodeFormat(String name) {
		this.name = name;
		VALUES.put(name, this);
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	public static BarcodeFormat valueOf(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException();
		}
		BarcodeFormat format = (BarcodeFormat) VALUES.get(name);
		if (format == null) {
			throw new IllegalArgumentException();
		}
		return format;
	}

}
