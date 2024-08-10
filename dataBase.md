## 表结构概览

#### **`article` (文章表)**

- `id`: 文章ID
- `original`: 是否原创
- `user_id`: 用户ID
- `category_name`: 分类名称
- `category_id`: 文章分类ID
- `title`: 文章标题
- `summary`: 文章摘要
- `content`: 文章内容
- `html_content`: 富文本内容
- `cover`: 封面图片
- `status`: 文章状态
- `view_count`: 浏览次数
- `comment_count`: 评论数
- `like_count`: 点赞数
- `collect_count`: 收藏数
- `publish_time`: 发布时间
- `update_time`: 更新时间
- `reproduce`: 转载地址
- `deleted`: 是否已删除

#### **`article_collect` (文章收藏表)**

- `id`: ID
- `user_id`: 用户ID
- `article_id`: 文章ID
- 索引确保每个用户只能收藏一篇文章一次

#### **`article_comment` (文章评论表)**

- `id`: ID
- `article_id`: 文章ID
- `from_user_id`: 评论者ID
- `content`: 评论内容
- `comment_time`: 评论时间
- `deleted`: 是否已删除

#### **`article_like` (文章点赞表)**

- `id`: ID
- `article_id`: 文章ID
- `user_id`: 用户ID
- 索引确保每个用户只能点赞一篇文章一次

#### **`article_reply` (文章回复表)**

- `id`: ID
- `article_id`: 文章ID
- `comment_id`: 评论ID
- `from_user_id`: 回复者ID
- `to_user_id`: 被回复者ID
- `content`: 回复内容
- `reply_time`: 回复时间
- `deleted`: 是否已删除

#### **`article_tag` (文章-标签关联表)**

- `id`: ID
- `article_id`: 文章ID
- `tag_id`: 标签ID

#### **`category` (分类表)**

- `id`: ID
- `name`: 名称
- `parent_id`: 父分类ID
- `deleted`: 是否已删除

#### **`client` (客户端表)**

- `id`: ID
- `client_id`: 客户端ID
- `client_secret`: 客户端密钥
- `access_token_expire`: access_token有效期
- `refresh_token_expire`: refresh_token有效期
- `enable_refresh_token`: 是否支持刷新token

#### **`friend_link` (友链表)**

- `id`: ID
- `name`: 名称
- `url`: 链接
- `icon`: 图标

#### **`leave_message` (留言表)**

- `id`: ID
- `pid`: 父留言ID
- `from_user_id`: 留言者ID
- `to_user_id`: 被留言者ID
- `content`: 内容
- `create_time`: 时间
- `deleted`: 是否删除

#### **`oauth_user` (第三方登录关联表)**

- `id`: ID
- `uuid`: 第三方平台用户唯一ID
- `user_id`: 用户ID
- `type`: 认证类型
- `create_time`: 创建时间

#### **`tag` (标签表)**

- `id`: ID
- `name`: 标签名
- `deleted`: 是否已删除

#### **`user` (用户表)**

- `id`: 用户ID
- `username`: 用户名
- `password`: 密码
- `mobile`: 手机号
- `nickname`: 昵称
- `gender`: 性别
- `birthday`: 生日
- `email`: 电子邮箱
- `brief`: 简介
- `avatar`: 头像
- `status`: 状态
- `admin`: 是否管理员
- `create_time`: 创建时间

### 关系说明

- 文章与用户
  - 通过`user_id`关联
- 文章与分类
  - 通过`category_id`关联
- 文章与标签
  - 通过`article_tag`表关联
- 文章与评论
  - 通过`article_id`关联
- 文章与点赞
  - 通过`article_like`表关联
- 文章与收藏
  - 通过`article_collect`表关联
- 文章与回复
  - 通过`article_reply`表关联
- 用户与第三方登录
  - 通过`oauth_user`表关联
- 用户与留言
  - 通过`leave_message`表关联