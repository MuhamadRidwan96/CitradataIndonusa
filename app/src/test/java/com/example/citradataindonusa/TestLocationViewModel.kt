package com.example.citradataindonusa

/*
@OptIn(ExperimentalCoroutinesApi::class)
class TestLocationViewModel {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var provinceUseCase: ProvinceUseCase
    private lateinit var cityUseCase: CityUseCase
    private lateinit var viewModel: LocationViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        provinceUseCase = mockk()
        cityUseCase = mockk()

        viewModel = LocationViewModel(
            dispatcher = testDispatcher,
            provinceUseCase = provinceUseCase,
            cityUseCase = cityUseCase
        )
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --------------------------------------------------------------------
    // PROVINCE TEST
    // --------------------------------------------------------------------
*/
/*

    @Test
    fun `getProvinces emits success and updates state`() = runTest {

        // Given
        val response = LocationDummy.provinceEmpty()
        coEvery { provinceUseCase(any()) } returns flow {
            emit(Result.success(response))
        }

        // When
        viewModel.getProvinces()
        testScheduler.advanceUntilIdle()

        // Then
        assert(viewModel.locationState.value.province is Result.success)
        coVerify(exactly = 1) { provinceUseCase(any()) }
    }

    // --------------------------------------------------------------------
    // CITY TEST
    // --------------------------------------------------------------------

    @Test
    fun `getCity returns success and updates state`() = runTest {

        val response = LocationDummy.citySuccess()

        coEvery { cityUseCase(any(), any(), any()) } returns flow {
            emit(Result.success(response))
        }

        // When
        viewModel.getCity("11")
        testScheduler.advanceUntilIdle()

        // Then
        assert(viewModel.locationState.value.cities is Result.success)
        coVerify(exactly = 1) { cityUseCase(any(), "11", any()) }
    }

    @Test
    fun `getCity returns error event`() = runTest {

        coEvery { cityUseCase(any(), any(), any()) } returns flow {
            throw RuntimeException("City error")
        }

        val events = mutableListOf<LocationEvent>()
        val job = launch { viewModel.provinceEvent.toList(events) }

        viewModel.getCity("11")
        testScheduler.advanceUntilIdle()

        assert(events.any { it is LocationEvent.Error })

        job.cancel()
    }

*//*


    // --------------------------------------------------------------------
    // CACHING LOGIC
    // --------------------------------------------------------------------

    @Test
    fun `getCity should NOT fetch twice for same province`() = runTest {

        val response = Result.success(RegenciesResponse(
            success = true  ,
            status = 200,
            message = "OK",
            data = emptyList()
        ))

      */
/*  coEvery { cityUseCase(any(), "11", any()) } returns flow {
            emit(
                response
            )
        }

        // Call first time
        viewModel.getCity("11")
        testScheduler.advanceUntilIdle()

        // Call second time — should not call useCase again
        viewModel.getCity("11")
        testScheduler.advanceUntilIdle()

        // Verify only called once
        coVerify(exactly = 1) { cityUseCase(any(), "11", any()) }*//*

    }


    // --------------------------------------------------------------------
    // STATE UPDATE TEST
    // --------------------------------------------------------------------

    @Test
    fun `updateCity should update selected fields`() = runTest {


    }

    @Test
    fun `clearProvince resets state`() = runTest {

    }
}*/
