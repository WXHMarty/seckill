//�����Ҫ�����߼�js����
//javascript ģ�黯
var seckill = {
		//��װ��ɱ���ajax��URL
		URL : {
			now : function(){
				return '/seckill/seckill/time/now';
			},
			exposer : function(seckillId){
				return '/seckill/seckill/' + seckillId + '/exposer';
			},
			execution : function(seckillId, md5){
				return '/seckill/seckill/' + seckillId + '/' + md5 + '/execute';
			}
		},
		
		//��֤�ֻ���
		validatePhone : function(phone) {
//			if(phone && phone.length == 11 && !isNaN(phone)){//isNaN(phone)�ж��Ƿ��Ƿ�����
//				return true;
//			}else{
//				return false;
//			}
			return true;
		},
		
		handleSeckill : function(seckillId, node){
			//������ɱ�߼�
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">��ʼ��ɱ</button>');
			$.post(
				seckill.URL.exposer(seckillId),
				{},
				function(result){
					//�ڻص�����ִ�н�������
					if(result && result['success']){
						var exposer = result['data'];
						if(exposer['exposer']){
							//������ɱ����ȡ��ɱ��ַ
							var md5 = exposer['md5'];
							var killUrl = seckill.URL.execution(seckillId, md5);
							console.log('killUrl:' + killUrl);
							//��һ�ε���¼�
							$('#killBtn').one('click', function(){
								//ִ����ɱ�������
								//1.���ð�ť
								$(this).addClass('disabled');
								//2.������ɱ����ִ����ɱ
								$.post(
									killUrl,
									{},
									function(result){
										if(result && result['success']){
											var killResult = result['data'];
											var stateInfo = result['error'];
											//3.��ʾ��ɱ���
											node.html('<span class="label label-success">' + stateInfo + '</span>');
										}
									}
								);
							});
							node.show();
						}else{
							//δ������ɱ
							var now = exposer['now'];
							var start = exposer['start'];
							var end = exposer['end'];
							//���¼����ʱ�߼�
							seckill.countdown(seckillId, now, start, end);
						}
					}else{
						console.log('result:' + result);
					}
				});
		},
		
		countdown : function(seckillId, nowTime, startTime, endTime){
			var seckillBox = $('#seckill-box');
			if(nowTime > endTime){
				//��ɱ����
				seckillBox.html('��ɱ�ѽ�����');
			}else if(nowTime < startTime){
				//��ɱδ��ʼ����ʱ
				var killTime = new Date(startTime + 1000);
				seckillBox.countdown(killTime, function(event){
					//ʱ���ʽ
					var format = event.strftime('��ɱ��ʱ��%D�� %Hʱ %M�� %S��');
					seckillBox.html(format);
				}).on('finish.countdown', function(){
					//��ȡ��ɱ��ַ��������ʾ�߼���ִ����ɱ
					seckill.handleSeckill(seckillId, seckillBox);
				});
			}else{
				//��ɱ������
				seckill.handleSeckill(seckillId, seckillBox);
			}
		},
		
		//����ҳ��ɱ�߼�
		detail : {
			//����ҳ��ʼ��
			init : function(params){
				//�ֻ���֤�͵�¼����ʱ����
				//�滮��������
				//��cookie�в����ֻ���
				var killPhone = $.cookie('killPhone');
				//��֤�ֻ���
				if(!seckill.validatePhone(killPhone)){
					//���killPhone���ԵĻ������ֻ���
					var killPhoneModal = $("#killPhoneModal");
					//��ʾ������
					killPhoneModal.modal({
						show : true,//��ʾ������
						backdrop : 'static',//��ֹλ�ùر�
						keyboard : false,//�رռ����¼�
					});
					$("#killPhoneBtn").click(function(){
						var inputPhone = $('#killPhoneKey').val();
						if(seckill.validatePhone(killPhone)){
							//���绰д��cookie
							$.cookie('killPhone', inputPhone, {expires : 7, path : '/seckill/seckill'});
							//ˢ��ҳ��
							window.location.reload();
						}else{
							$('#killPhoneMessage').hide().html('<label class="label label-danger">�ֻ��Ŵ���</label>').show(300);
						}
					});
				}
				
				//�Ѿ���¼�Ժ�
				//��ʱ����
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				var seckillId = params['seckillId'];
				$.get(
					seckill.URL.now(),
					{},
					function(result){
						if(result && result['success']){
							var nowTime = result['data'];
							//ʱ���жϣ���ʱ����
							seckill.countdown(seckillId, nowTime, startTime, endTime);
						}else{
							console.log('result:' + result);
						}
					}
				);
			}
		}
};