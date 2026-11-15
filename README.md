# Exposure: Polaroid (Port)

A community port of **mortuusars' Exposure: Polaroid** addon, for **Minecraft 26.1 / Fabric**.
Aligned with the self-maintained base mod **Exposure New Port** (`exposure` ≥ 26.1.1).

> This is a community port. It is **not** official work by mortuusars.

---

## 简介 / About

本模组是 mortuusars 的 **Exposure: Polaroid** 附属模组的社区移植版本，适配 **Minecraft 26.1 / Fabric**，
与自行维护的本体 **Exposure New Port**（`exposure` ≥ 26.1.1）对齐。

> 社区移植版本，非原作者 mortuusars 官方作品。

## 功能 / Features

- 拍立得即时相机（Instant Polaroid Camera）与四款即时相纸
- 拍摄后立刻生成照片，无需冲洗流程
- 保留上游原版的音效、物品资源与交互逻辑

- Instant Polaroid camera plus four types of instant film slides
- Photos are produced immediately after capture — no developing process
- Original upstream sounds, item assets and interaction behavior preserved

## 依赖 / Dependencies

| 依赖 | 版本 |
|---|---|
| Minecraft | 26.1 |
| Fabric Loader | ≥ 0.16.0 |
| Fabric API | 对应 26.1 的构建 |
| Forge Config API Port | ≥ 21.0.0 |
| Java | ≥ 25 |
| **Exposure New Port** (`exposure`) | **≥ 26.1.1** |

## 安装 / Installation

1. 安装 Fabric Loader（对应 Minecraft 26.1）
2. 将 **Exposure New Port** 与 **Exposure: Polaroid (Port)** 的 jar 一同放入 `mods` 文件夹
3. 确保同时安装对应 26.1 版本的 Fabric API
4. 启动游戏

## 从源码构建 / Building from source

```bash
# 需要 JDK 25
./gradlew build
```

构建产物位于 `fabric/build/libs/exposure_polaroid-fabric-26.1-<version>.jar`。

本体 `exposure` 需先发布到本地 Maven（`publishToMavenLocal`），本项目依赖其 `26.1.x` 版本。

## 已知问题 / Known Issues

- 相机镜头动态着色（lens tint）暂未实现：Exposure New Port 26.1 移除了旧的物品颜色提供器 API。
  仅影响手持模型的外观细节，不影响拍照、出片与全部玩法。（后续版本会修复）
- 仅支持 Fabric，不提供 NeoForge 版本。

- Camera lens tint is not implemented (the legacy item color provider API was removed in Exposure New Port 26.1).
  Cosmetic-only; capture, printing and all gameplay are unaffected.
- Fabric only; no NeoForge support.

## 许可证 / License

[MIT](LICENSE) —— 上游 Exposure: Polaroid 采用 MIT 许可证，本移植版本遵循同一许可证。

- 原作者 / Original author: **mortuusars**
- 移植者 / Port author: **Suiyun**

## 致谢 / Credits

- [mortuusars](https://github.com/mortuusars) — Exposure 与 Exposure: Polaroid 原作者
- [ExposurePolaroid](https://github.com/mortuusars/ExposurePolaroid) — 上游仓库
