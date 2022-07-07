package com.youngplussoft.admin.common

import java.io.Closeable

interface ClosableJob : Closeable, Runnable
