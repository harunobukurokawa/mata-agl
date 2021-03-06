FILESEXTRAPATHS_prepend := "${THISDIR}/linux-yocto:"

# Extra configuration options for the QEMU kernel
SRC_URI += "file://hciattach.cfg \
            "

# Enable some things on qemuarm64 so MOST drivers will build and load.
SRC_URI_append_qemuarm64 = " file://most_deps.cfg"

# Build a generic v7 kernel instead of the arm926j one that upstream
# qemuarm defaults to.
KBUILD_DEFCONFIG_qemuarm = "multi_v7_defconfig"
KCONFIG_MODE = "--alldefconfig"
