package com.chow.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chow.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnDrawListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[ViewModel::class.java] }
    private var isSettingShown = true
    private var isEraser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            vDrawing.setDrawingImage(data[0])
            btnChangeColor.apply {
                setOnClickListener {
                    ColorSelectorDialog(onColorSelected = {
                        setBackgroundColor(it)
                        vDrawing.changeColor(it)
                    }).show(supportFragmentManager, ColorSelectorDialog::class.java.simpleName)
                }
            }
            vDrawing.setOnDrawListener(this@MainActivity)
            btnClear.setOnClickListener {
                vDrawing.clearDrawing(true)
            }
            btnMode.apply {
                setOnClickListener {
                    vDrawing.switchMode(isEraser.not().also { isEraser = it })
                    text = if (isEraser) "eraser" else "pen"
                }
            }
            ivArrow.apply {
                setOnClickListener {
                    if (isSettingShown) {
                        isSettingShown = false
                        setImageResource(R.drawable.ic_arrow_up)
                        mlParent.apply {
                            setTransition(R.id.transition_close)
                            transitionToEnd()
                        }
                    } else {
                        isSettingShown = true
                        setImageResource(R.drawable.ic_arrow_down)
                        mlParent.apply {
                            setTransition(R.id.transition_open)
                            transitionToEnd()
                        }
                    }
                }
            }
            btnUndo.setOnClickListener {
                vDrawing.undoLastDraw()
            }
            vGuess.setAnswerSize(5)
        }
        viewModel.line.observe(this) {
            binding.vDrawing.apply {
                if (it == null) {
                    clearDrawing(false)
                } else {
                    addNewLine(it)
                }
            }
        }
    }

    override fun onDraw(line: Line?) {
        viewModel.drawNewLine(line)
    }
}