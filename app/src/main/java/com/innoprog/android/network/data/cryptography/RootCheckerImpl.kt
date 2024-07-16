package com.innoprog.android.network.data.cryptography

import android.content.Context
import android.content.pm.PackageManager
import com.innoprog.android.network.domain.cryptography.RootChecker
import java.io.File
import javax.inject.Inject

class RootCheckerImpl @Inject constructor() : RootChecker {

    override fun isDeviceRooted(context: Context): Boolean {
        return checkForRootFiles() || checkForRootPackages(context) || checkForRootBinaries() || checkForSuCommand()
    }

    private fun checkForRootFiles(): Boolean {
        val rootPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/system/bin/su",
            "/system/xbin/su",
            "/system/sbin/su",
            "/sbin/su",
            "/etc/init.d/99SuperSUDaemon"
        )
        return rootPaths.any { File(it).exists() }
    }

    @Suppress("SwallowedException")
    private fun checkForRootPackages(context: Context): Boolean {
        val rootPackages = arrayOf(
            "eu.chainfire.supersu",
            "com.noshufou.android.su",
            "com.thirdparty.superuser",
            "com.yellowes.su",
            "com.topjohnwu.magisk"
        )
        val packageManager = context.packageManager
        return rootPackages.any {
            try {
                packageManager.getPackageInfo(it, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }

    private fun checkForRootBinaries(): Boolean {
        val binaryPaths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/bin/mu",
            "/system/xbin/mu"
        )
        return binaryPaths.any { File(it).exists() && isExecutable(File(it)) }
    }

    private fun isExecutable(file: File): Boolean {
        return file.canExecute()
    }

    private fun checkForSuCommand(): Boolean {
        return runCatching {
            Runtime.getRuntime()
                .exec(arrayOf("/system/xbin/which", "su")).inputStream.bufferedReader()
                .readLine() != null
        }.getOrNull() ?: false
    }
}