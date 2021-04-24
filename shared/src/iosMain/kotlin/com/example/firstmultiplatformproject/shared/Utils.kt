package com.example.firstmultiplatformproject.shared

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()