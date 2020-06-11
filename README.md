# AutoScrollRecyclerView
自动滚动的RecyclerView

## API

```
//设置无限滚动
AutoScrollRecyclerView.setLoopEnabled(true);
//开启自动滚动
AutoScrollRecyclerView.openAutoScroll();
//设置反向滚动
AutoScrollRecyclerView.setReverse(boolean);
//设置列表是否可手动滑动
AutoScrollRecyclerView.setCanTouch(boolean);
```
## 原理
### 一、自动滚动实现
 * 定义Interpolator，实现线性滚动
 * smoothScrollBy 实现滚动效果
 * 监听onScrolled方法，判断滑动结束时再次启动滑动

### 二、无限循环
自定义Adapter，重写getItemCount方法，返回int无限大值


## 升级篇
使用LayoutManager + RecyclerView.OnItemTouchListener + RecyclerView.OnScrollListener实现该功能。

### ---缺点：未实现不可打断效果

通过 ASLinearLayoutManager， ASGridLayoutManager， ASStaggeredGridLayoutManager 三个LayoutManager实现。

通过LayoutManager中LayoutManagerHelper进行配置