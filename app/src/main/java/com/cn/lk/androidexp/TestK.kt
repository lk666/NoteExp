package com.cn.lk.androidexp

object TestK {
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        nums.sort()
        val res = mutableListOf<List<Int>>()
        for ((index, value) in nums.withIndex()) {
            if (index == 0 || value != nums[index - 1]) {
                val subRes = threeSum(nums, index + 1, target - value, value)
                if (!subRes.isEmpty()) {
                    res.addAll(subRes)
                }
            }
        }
        return res
    }

    fun threeSum(nums: IntArray, start: Int, target: Int, ext: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        var left = start
        var right = nums.size - 2
        while (left < right) {
            val subRes = twoSum(nums, left + 1, target - nums[left], nums[left], ext)
            if (!subRes.isEmpty()) {
                res.addAll(subRes)
            }
            do {
                left++
            } while (left < right && nums[left] == nums[left - 1])
        }
        return res
    }

    /**
     * 所有的两数和集合（去重）变种
     */
    fun twoSum(nums: IntArray, start: Int, target: Int, vararg ext: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        var left = start
        var right = nums.size - 1
        while (left < right) {
            val sum = nums[left] + nums[right]
            when {
                sum < target -> do {
                    left++
                } while (left < right && nums[left] == nums[left - 1])
                sum > target -> do {
                    right--
                } while (left < right && nums[right] == nums[right + 1])
                else -> {
                    var l = mutableListOf<Int>(nums[left], nums[right])
                    for (prex in ext) {
                        l.add(0, prex)
                    }
                    res.add(l)
                    do {
                        left++
                    } while (left < right && nums[left] == nums[left - 1])
                }
            }
        }
        return res
    }
}