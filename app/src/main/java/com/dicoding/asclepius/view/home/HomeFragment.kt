package com.dicoding.asclepius.view.home

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.ImageClassifierHelperFactory
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var imageClassifierHelperFactory: ImageClassifierHelperFactory

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let { cropImage(it) } ?: showToast("Failed to select an image")
    }

    // override fun onActivityResult is deprecated and kinda doesn't work with fragment? Somehow
    private val launcherCrop = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) viewModel.setUri(UCrop.getOutput(result.data!!))
        else if (result.resultCode == UCrop.RESULT_ERROR) showToast(UCrop.getError(result.data!!)?.message ?: "Unknown error")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeImage()

        imageClassifierHelperFactory = ImageClassifierHelperFactory(requireContext())
        initializeClassifier()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun observeImage() {
        viewModel.uri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.previewImageView.setImageURI(it)
                // if uri is not null, enable analyze button
                binding.analyzeButton.isEnabled = true
            } ?: run {
                binding.analyzeButton.isEnabled = false
            }
        }
    }

    private fun cropImage(uri: Uri) {
        val destinationUri = Uri.fromFile(requireContext().cacheDir.resolve("${System.currentTimeMillis()}.jpg"))
        val uCrop = UCrop.of(uri, destinationUri)
        launcherCrop.launch(uCrop.getIntent(requireContext()))
    }

    private fun initializeClassifier() {
        imageClassifierHelper = imageClassifierHelperFactory.create(
            // Since its in fragment we use viewLifecycleOwner instead of runOnUiThread
            onError = { viewLifecycleOwner.lifecycleScope.launch {
                displayLoading(false)
                showToast(it)
            }}
        ) { results, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                results?.let { classifications ->
                    if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                        classifications[0].categories.maxByOrNull {
                            it.score
                        }?.let {
                            val classificationResult = ClassificationResult(
                                uri = viewModel.uri.value.toString(),
                                label = it.label,
                                score = it.score
                            )
                            displayLoading(false)
                            moveToResult(classificationResult)
                        }
                    } else {
                        displayLoading(false)
                        showToast("No results found")
                    }
                }
            }
        }
    }

    private fun moveToResult(result: ClassificationResult) {
        val toResult =  HomeFragmentDirections.actionNavigationHomeToResultActivity(result, false)
        findNavController().navigate(toResult)
    }

    private fun analyzeImage() {
        viewModel.uri.value?.let {
            displayLoading(true)
            imageClassifierHelper.classifyStaticImage(it)
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
