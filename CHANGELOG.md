# Changelog

All notable changes to this port will be documented here.

---

## [26.1.1] — Minecraft 26.1 / Fabric

Full community Fabric port of mortuusars' **Exposure: Polaroid** addon for Minecraft 26.1.
Requires **Exposure New Port ≥ 26.1.1**.

### Features
- Adds the instant Polaroid camera and four types of instant film slides
- Photos output immediately after capture — no developing process required
- Original sounds, item assets and behavior from upstream preserved

### Porting & Fixes
- Adapted to Minecraft 26.1 API changes (`ResourceLocation` → `Identifier`, GUI rendering, widget system)
- Rewrote camera item rendering using the new `ItemModel` composite system; removed the old `ItemProperties` mixin
- Fixed game-start `NullPointerException` by adding the mandatory `.setId()` for all items
- Updated config registry (`ConfigRegistry` v5) and creative tab injection (`CreativeModeTabEvents`) for Fabric API 26.1
- Added the missing `items/` model declarations, fixing the purple-black missing-texture mosaic on instant film slides

### Known Issues
- Camera lens dynamic tint is not implemented (legacy item color provider API removed in Exposure New Port 26.1).
  Cosmetic-only issue; gameplay is unaffected. Planned for a future update.
- Fabric only; no NeoForge support.

### Dependencies
- Exposure New Port: >= 26.1.1
- Fabric API: matching 26.1 build

---

## [26.1.1] — Minecraft 26.1 / Fabric（中文）

mortuusars 的 **Exposure: Polaroid** 附属模组面向 Minecraft 26.1 的完整社区 Fabric 移植版本。
依赖 **Exposure New Port ≥ 26.1.1**。

### 功能
- 添加拍立得即时相机与四款即时相纸
- 拍摄后立刻生成照片，无需冲洗流程
- 保留上游原版的音效、物品资源与行为逻辑

### 移植与修复
- 适配 Minecraft 26.1 的 API 变更（`ResourceLocation` → `Identifier`、GUI 渲染、控件系统）
- 使用全新 `ItemModel` composite 复合系统重写相机物品渲染，移除旧的 `ItemProperties` Mixin
- 为所有物品补全强制要求的 `.setId()`，修复游戏启动空指针崩溃
- 适配 Fabric API 26.1 的配置注册（`ConfigRegistry` v5）与创造模式物品栏注入（`CreativeModeTabEvents`）
- 补全缺失的 `items/` 模型声明文件，修复即时相纸紫黑马赛克纹理丢失问题

### 已知问题
- 相机镜头动态着色暂未实现（Exposure New Port 26.1 移除了旧版物品颜色提供器 API）。
  仅外观问题，不影响玩法，后续版本修复。
- 仅支持 Fabric，不提供 NeoForge 版本。

### 依赖
- Exposure New Port：>= 26.1.1
- Fabric API：对应 26.1 的构建版本
