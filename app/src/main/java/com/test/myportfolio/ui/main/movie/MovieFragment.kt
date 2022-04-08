package com.test.myportfolio.ui.main.movie

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentMovieBinding
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.databinding.ItemMovieBinding
import com.test.myportfolio.util.PreferenceManager
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment :
    BaseBindingFragment<FragmentMovieBinding, MovieViewModel>(MovieViewModel::class.java) {

    override fun onBackEvent() {

    }

    val sharedPreference: PreferenceManager by inject()

    override fun getResourceId(): Int = R.layout.fragment_movie

    override val viewModel: MovieViewModel by viewModel()

    override fun initData(): Boolean = true

    override fun setVariable(): Int = BR.movie

    // Item의 클릭 상태를 저장할 array 객체
    val selectedItems = SparseBooleanArray()

    // 직전에 클릭됐던 Item의 position
    var prePosition = -1


    var animationDelay = true
    var animationFlag = true


//    val selected = PublishSubject.create<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getMovieList()

//        binding.refreshLayoutMovie.setOnRefreshListener {
//            viewModel.searchBtn()
//            binding.refreshLayoutMovie.isRefreshing = false
//        }

        viewModel.movieItems.observe(viewLifecycleOwner, {
            if (it.size == 0) {
                selectedItems.clear()
                prePosition = -1
//                selected.
            }
        })

        binding.rvMovie.apply {
            adapter = object : BaseRecyclerAdapter.Create<MovieModel.RS.List, ItemMovieBinding>(
                R.layout.item_movie,
                bindingVariableId = BR.itemMovie,
                bindingListener = BR.movieItemListener
            ) {
                override fun onItemClick(item: MovieModel.RS.List, view: View) {
                    if (view.tag == "img") {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
                    } else {
                        if (selectedItems.get(item.id)) {
                            // 펼쳐진 Item을 클릭 시
                            selectedItems.delete(item.id)
                        } else {
                            // 직전의 클릭됐던 Item의 클릭상태를 지움
                            selectedItems.delete(prePosition)
                            // 클릭한 Item의 position을 저장
                            selectedItems.put(item.id, true)
                        }
//                    selected.onNext(selectedItems.size() != 0)
                        // 해당 포지션의 변화를 알림
                        if (prePosition != -1) notifyItemChanged(prePosition)
                        notifyItemChanged(item.id)
                        // 클릭된 position 저장
                        prePosition = item.id
                    }
                }

                override fun onBindViewHolder(
                    holder: BaseRecyclerAdapter.BaseViewHolder<ItemMovieBinding>,
                    position: Int
                ) {
                    super.onBindViewHolder(holder, position)
                    if (holder.binding is ItemMovieBinding) {
                        holder.binding.llContent.visibility =
                            if (selectedItems.get(position)) View.VISIBLE else View.GONE
                    }
                }
            }

            val toTop = AnimationUtils.loadAnimation(context, R.anim.to_top)
            val toTop2 = ObjectAnimator.ofFloat(binding.rvMovie,"translationY",-150f,0f).apply {
                duration = 500

            }
            val fromTop = AnimationUtils.loadAnimation(context, R.anim.from_top)
            val fromTop2 = ObjectAnimator.ofFloat(binding.rvMovie,"translationY",0f,-150f).apply {
                duration = 500
            }
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.nextPage()
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 && animationDelay && animationFlag) {
                        binding.clTop.startAnimation(fromTop)
                        fromTop2.start()

                        fromTop.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                                animationFlag = false
                                animationDelay = false
                            }
                            override fun onAnimationEnd(animation: Animation?) {
//                                binding.clTop.visibility = View.GONE
                                animationDelay = true
                            }
                            override fun onAnimationRepeat(animation: Animation?) { }
                        })
                    } else if (dy < 0 && animationDelay && !animationFlag) {
                        binding.clTop.startAnimation(toTop)
                        toTop2.start()

                        toTop.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
//                                binding.clTop.visibility = View.VISIBLE
                                animationFlag = true
                                animationDelay = false

                            }
                            override fun onAnimationEnd(animation: Animation?) {
                                animationDelay = true
                            }
                            override fun onAnimationRepeat(animation: Animation?) { }
                        })
                    }
                }
            })
        }
    }
}